package com.douzone.knitching.global.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

/**
 * HTTP 요청에서 JWT 토큰을 추출하고 관리하는 유틸리티 클래스
 */
@Component
public class JwtTokenProvider {
    private static final String BEARER_PREFIX = "Bearer ";
    private static final int BEARER_PREFIX_LENGTH = BEARER_PREFIX.length();

    @Autowired
    private JwtProvider jwtProvider;

    /**
     * HTTP 헤더에서 Authorization 토큰 추출
     *
     * @param authHeader Authorization 헤더 값
     * @return 토큰 값 (Bearer 제거됨), null이면 토큰이 없음
     */
    public String extractTokenFromHeader(String authHeader) {
        if (authHeader == null || !authHeader.startsWith(BEARER_PREFIX)) {
            return null;
        }
        return authHeader.substring(BEARER_PREFIX_LENGTH);
    }

    /**
     * Authorization 헤더가 유효한 형식인지 확인
     *
     * @param authHeader Authorization 헤더 값
     * @return 유효한 형식이면 true
     */
    public boolean isValidAuthHeaderFormat(String authHeader) {
        return authHeader != null && authHeader.startsWith(BEARER_PREFIX);
    }

    /**
     * Authorization 헤더에서 토큰을 추출하여 사용자 ID 반환
     *
     * @param authHeader Authorization 헤더 값
     * @return 사용자 ID
     * @throws IllegalArgumentException 토큰이 없거나 유효하지 않으면 예외 발생
     */
    public Long getUserIdFromAuthHeader(String authHeader) {
        String token = extractTokenFromHeader(authHeader);
        if (token == null) {
            throw new IllegalArgumentException("Authorization 헤더에 토큰이 없습니다");
        }
        return jwtProvider.getUserId(token);
    }

    /**
     * Authorization 헤더 유효성 검증
     *
     * @param authHeader Authorization 헤더 값
     * @throws IllegalArgumentException 토큰이 없거나 유효하지 않으면 예외 발생
     */
    public void validateAuthHeader(String authHeader) {
        String token = extractTokenFromHeader(authHeader);
        if (token == null) {
            throw new IllegalArgumentException("Authorization 헤더에 토큰이 없습니다");
        }
        if (!jwtProvider.validateToken(token)) {
            throw new IllegalArgumentException("유효하지 않은 토큰입니다");
        }
    }

    /**
     * Bearer 토큰으로 포맷된 토큰 반환
     *
     * @param token JWT 토큰
     * @return "Bearer {token}" 형식의 문자열
     */
    public String formatBearerToken(String token) {
        return BEARER_PREFIX + token;
    }
}
