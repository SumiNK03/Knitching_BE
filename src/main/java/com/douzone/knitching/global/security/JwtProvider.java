package com.douzone.knitching.global.security;

import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Date;

/**
 * JWT 토큰 생성 및 검증을 담당하는 클래스
 * userId 기반의 간단한 토큰 관리
 */
@Component
public class JwtProvider {
    @Value("${jwt.secret:thisisaverysecuresecretkey123456}")
    private String secret;

    @Value("${jwt.access-token-validity:86400000}") // 24시간(밀리초)
    private long accessTokenValidTime;

    private SecretKey key;

    /**
     * JWT 토큰 생성
     *
     * @param userId 사용자 ID
     * @return 생성된 JWT 토큰
     */
    public String createToken(Long userId) {
        return createToken(String.valueOf(userId));
    }

    /**
     * JWT 토큰 생성
     *
     * @param userId 사용자 ID (문자열)
     * @return 생성된 JWT 토큰
     */
    public String createToken(String userId) {
        initializeKey();
        Date now = new Date();
        Date expiry = new Date(now.getTime() + accessTokenValidTime);

        return Jwts.builder()
                .setSubject(userId)
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * JWT 토큰에서 클레임 추출
     *
     * @param token JWT 토큰
     * @return 토큰에서 추출된 클레임 정보
     */
    public Claims getClaims(String token) {
        initializeKey();
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    /**
     * JWT 토큰에서 사용자 ID 추출
     *
     * @param token JWT 토큰
     * @return 토큰에서 추출된 사용자 ID
     */
    public Long getUserId(String token) {
        return Long.parseLong(getClaims(token).getSubject());
    }

    /**
     * JWT 토큰 유효성 검사
     *
     * @param token JWT 토큰
     * @return 토큰이 유효한 경우 true, 그렇지 않은 경우 false
     */
    public boolean validateToken(String token) {
        try {
            getClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 토큰이 만료되었는지 확인
     *
     * @param token JWT 토큰
     * @return 토큰이 만료되었으면 true
     */
    public boolean isTokenExpired(String token) {
        try {
            return getClaims(token).getExpiration().before(new Date());
        } catch (Exception e) {
            return true;
        }
    }

    /**
     * 키 초기화 (lazy initialization)
     */
    private void initializeKey() {
        if (key == null) {
            key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        }
    }
}
