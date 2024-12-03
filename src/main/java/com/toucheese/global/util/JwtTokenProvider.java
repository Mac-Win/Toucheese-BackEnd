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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JwtTokenProvider {

    private final SecretKey secretKey;

    public JwtTokenProvider(AppConfig appConfig) {
        this.secretKey = Keys.hmacShaKeyFor(appConfig.getSecretKey());
    }

    public String createAccessToken(String id) {
        Date now = new Date();
        return Jwts.builder()
                .subject(id)
                .signWith(secretKey)
                .expiration(new Date(now.getTime() + (1000L * 60 * 30))) // 30분
                .issuedAt(now)
                .compact();
    }

    public String createRefreshToken(String id) {
        Date now = new Date();
        return Jwts.builder()
                .subject(id)
                .signWith(secretKey)
                .expiration(new Date(now.getTime() + (1000L * 60 * 60 * 24 * 30 * 3))) // 3개월
                .issuedAt(now)
                .compact();
    }

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

    public Authentication getAuthentication(String token) {
        Claims claims = getClaims(token);
        User principal = new User(claims.getSubject(), "",  Collections.emptyList());
        return new UsernamePasswordAuthenticationToken(principal, token, principal.getAuthorities());
    }

}
