package com.toucheese.member.service;

import com.toucheese.global.exception.ToucheeseUnAuthorizedException;
import com.toucheese.global.util.JwtTokenProvider;
import com.toucheese.member.entity.Member;
import com.toucheese.member.entity.Token;
import com.toucheese.member.repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final JwtTokenProvider jwtTokenProvider;
    private final TokenRepository tokenRepository;

    /**
     * 갱신 토큰 검색을 위한 메서드
     * @param accessToken 접근 토큰
     * @return 해당하는 토큰 정보
     */
    @Transactional(readOnly = true)
    public Token findRefreshTokenByAccessToken(String accessToken) {
        return tokenRepository.findByAccessToken(accessToken)
                .orElseThrow(ToucheeseUnAuthorizedException::new);
    }

    /**
     * 토큰 저장을 위한 메서드
     * @param member 회원
     */
    @Transactional
    public String saveToken(Member member) {
        String memberId = member.getId().toString();
        String accessToken = jwtTokenProvider.createAccessToken(memberId);
        String refreshToken = jwtTokenProvider.createRefreshToken(memberId);

        Token token = Token.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .member(member)
                .build();

        tokenRepository.save(token);
        return accessToken;
    }

    /**
     * 접근 토큰 정보 업데이트를 위한 메서드
     * @param token 해당하는 토큰
     * @param accessToken 새로운 갱신 토큰
     */
    @Transactional
    public void updateAccessToken(Token token, String accessToken) {
        token.updateAccessToken(accessToken);
    }

}
