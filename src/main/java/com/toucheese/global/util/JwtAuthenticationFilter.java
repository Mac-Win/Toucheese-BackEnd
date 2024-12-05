package com.toucheese.global.util;

import com.toucheese.global.exception.ToucheeseTokenInvalidException;
import com.toucheese.member.entity.Token;
import com.toucheese.member.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final TokenService tokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String accessToken = getTokenFromAuthorizationHeader(request);

        if (!StringUtils.hasText(accessToken)) {
            filterChain.doFilter(request, response);
            return;
        }

        if (!jwtTokenProvider.validateToken(accessToken)){ // accessToken 인증 되지 않은 경우
            String memberId = jwtTokenProvider.getClaims(accessToken).getSubject();

            Token token = tokenService.findRefreshTokenByUserIdAndAccessToken(Long.parseLong(memberId), accessToken);
            checkRefreshToken(token.getRefreshToken()); // refreshToken 만료 확인

            String newAccessToken = jwtTokenProvider.createAccessToken(accessToken);
            tokenService.updateAccessToken(token, newAccessToken); // 갱신 및 업데이트

            response.setHeader("Authorization", "Bearer " + newAccessToken); // 새로운 accessToken을 응답 헤더에 추가
        }

        Authentication authentication = jwtTokenProvider.getAuthentication(accessToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }

    /**
     * RefreshToken의 만료를 확인 및 처리하기 위한 메서드
     * @param refreshToken 갱신 토큰
     * @throws ToucheeseTokenInvalidException 갱신 토큰 또한 만료되었을 경우 재로그인 요청
     */
    private void checkRefreshToken(String refreshToken) {
        if (!jwtTokenProvider.validateToken(refreshToken)) {
            throw new ToucheeseTokenInvalidException();
        }
    }

    /**
     * 인증 헤더를 확인하여 AccessToken을 반환하기 위한 메서드
     * @param request 요청 정보
     * @return AccessToken
     */
    private String getTokenFromAuthorizationHeader(HttpServletRequest request) {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }

        return "";
    }
}
