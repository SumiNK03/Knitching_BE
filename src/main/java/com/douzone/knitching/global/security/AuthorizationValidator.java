package com.douzone.knitching.global.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * JWT 토큰 기반의 인증을 담당하는 클래스
 * 토큰 유효성 검증 및 사용자 정보 추출
 */
@Component
public class AuthorizationValidator {
    
    @Autowired
    private JwtProvider jwtProvider;

    /**
     * 토큰 유효성 검증
     *
     * @param token JWT 토큰
     * @throws IllegalArgumentException 토큰이 유효하지 않으면 예외 발생
     */
    public void validateToken(String token) {
        if (!jwtProvider.validateToken(token)) {
            throw new IllegalArgumentException("유효하지 않은 토큰입니다");
        }
    }

    /**
     * 토큰에서 사용자 ID 추출
     *
     * @param token JWT 토큰
     * @return 사용자 ID
     * @throws IllegalArgumentException 토큰이 유효하지 않으면 예외 발생
     */
    public Long getUserIdFromToken(String token) {
        validateToken(token);
        return jwtProvider.getUserId(token);
    }

    /**
     * 사용자가 자신의 데이터에만 접근하는지 확인
     * (자신의 정보만 조회하도록 제어)
     *
     * @param token JWT 토큰
     * @param userId 확인할 사용자 ID
     * @return 일치하면 true
     */
    public boolean isOwner(String token, Long userId) {
        try {
            Long tokenUserId = getUserIdFromToken(token);
            return tokenUserId.equals(userId);
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
        return jwtProvider.isTokenExpired(token);
    }
}
