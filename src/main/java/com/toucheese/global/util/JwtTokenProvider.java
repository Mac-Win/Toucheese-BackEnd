package com.toucheese.global.util;

import com.toucheese.global.config.AppConfig;
import com.toucheese.global.exception.ToucheeseUnAuthorizedException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.util.Collections;
import java.util.Date;
import javax.crypto.SecretKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JwtTokenProvider {

    private final SecretKey secretKey;

    @Value("${jwt.access-token-expiration}")
    private Long accessTokenExpiration;
    @Value("${jwt.refresh-token-expiration}")
    private Long refreshTokenExpiration;


    public JwtTokenProvider(AppConfig appConfig) {
        this.secretKey = Keys.hmacShaKeyFor(appConfig.getSecretKey());
    }

    /**
     * 접근 토큰 생성을 위한 메서드
     * @param memberId subject 등록을 위한 회원 아이디
     * @return 생성된 접근 토큰
     */
    public String createAccessToken(String memberId) {
        Date now = new Date();
        return Jwts.builder()
                .subject(memberId)
                .signWith(secretKey)
                .expiration(new Date(now.getTime() + accessTokenExpiration)) // 30분
                .issuedAt(now)
                .compact();
    }

    /**
     * 갱신 토큰 생성을 위한 메서드
     * @param memberId subject 등록을 위한 회원 아이디
     * @return 생성된 갱신 토큰
     */
    public String createRefreshToken(String memberId) {
        Date now = new Date();
        return Jwts.builder()
                .subject(memberId)
                .signWith(secretKey)
                .expiration(new Date(now.getTime() + refreshTokenExpiration)) // 3개월
                .issuedAt(now)
                .compact();
    }

    /**
     * 토큰 내 정보를 확인하기 위한 메서드
     * @param token 접근 토큰 혹은 갱신 토큰
     * @return 토큰 내 정보
     */
    public Claims getClaims(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (ExpiredJwtException e) {
            throw new ToucheeseUnAuthorizedException();
        }
    }

    /**
     * 토큰 검증을 위한 메서드
     * @param token 접근 토큰 혹은 갱신 토큰
     * @return 정상적으로 verify 된다면 true, 아니라면 false
     */
    public boolean validateToken(String token) {
        try{
            Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch(JwtException e) {
            return false;
        }
    }

    /**
     * 인증 정보 설정을 위한 메서드
     * @param token 접근 토큰
     * @return 인증 정보
     */
    public Authentication getAuthentication(String token) {
        Claims claims = getClaims(token);
        User principal = new User(claims.getSubject(), "",  Collections.emptyList());
        return new UsernamePasswordAuthenticationToken(principal, token, principal.getAuthorities());
    }

}
