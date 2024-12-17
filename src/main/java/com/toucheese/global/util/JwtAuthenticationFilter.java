package com.toucheese.global.util;

import com.toucheese.global.data.JwtValidateStatus;
import com.toucheese.global.exception.ToucheeseInternalServerErrorException;
import com.toucheese.global.exception.ToucheeseUnAuthorizedException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final TokenUtils tokenUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String accessToken = tokenUtils.getTokenFromAuthorizationHeader(request);

        if (StringUtils.hasText(accessToken)) {
            JwtValidateStatus validateStatus = jwtTokenProvider.validateToken(accessToken);

            switch (validateStatus) {
                case DENIED:
                    SecurityContextHolder.clearContext();
                    throw new ToucheeseUnAuthorizedException("올바르지 않은 토큰입니다.");
                case EXPIRED:
                    SecurityContextHolder.clearContext();
                    throw new ToucheeseUnAuthorizedException("토큰이 만료되었습니다.");
                case ACCEPTED:
                    Authentication authentication = jwtTokenProvider.getAuthentication(accessToken);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    break;
                default:
                    throw new ToucheeseInternalServerErrorException("올바르지 않은 토큰 상태입니다.");
            }
        }

        filterChain.doFilter(request, response);
    }

}
