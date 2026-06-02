package com.douzone.knitching.global.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * JWT 토큰 기반의 인증 필터
 * 모든 HTTP 요청에서 Authorization 헤더를 검사하고 JWT 토큰을 검증합니다
 */
@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;
    
    // 필터 제외 경로 목록 (인증 없이 접근 가능)
    private static final List<String> EXCLUDED_PATHS = Arrays.asList(
        "/swagger-ui.html",
        "/swagger-ui",
        "/v3/api-docs",
        "/api/users/login",
        "/api/users/register",
        "/api/instructors/login",
        "/api/instructors/register"
    );

    public JwtFilter(JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    /**
     * HTTP 요청에서 JWT 토큰을 추출하고 유효성을 검사하는 필터 메서드
     *
     * @param request     HTTP 요청 객체
     * @param response    HTTP 응답 객체
     * @param filterChain 필터 체인 객체
     * @throws ServletException 예외 발생 시 던지는 서블릿 예외
     * @throws IOException      예외 발생 시 던지는 입출력 예외
     */
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        String requestPath = request.getRequestURI();
        
        // 제외 경로인 경우 필터 스킵
        if (isExcludedPath(requestPath)) {
            filterChain.doFilter(request, response);
            return;
        }

        // HTTP 요청에서 JWT 토큰 추출
        String token = resolveToken(request);

        // JWT 토큰이 존재하고 유효한 경우 요청 속성에 userId 저장
        if (token != null && jwtProvider.validateToken(token)) {
            // JWT 토큰에서 사용자 ID 추출
            Long userId = jwtProvider.getUserId(token);
            
            // 요청 속성에 userId 저장 (컨트롤러에서 접근 가능)
            request.setAttribute("userId", userId);
        }

        // 다음 필터로 요청 전달
        filterChain.doFilter(request, response);
    }

    /**
     * 요청 경로가 필터 제외 경로인지 확인
     *
     * @param requestPath 요청 경로
     * @return 제외 경로이면 true
     */
    private boolean isExcludedPath(String requestPath) {
        return EXCLUDED_PATHS.stream()
                .anyMatch(excluded -> {
                    // 정확한 경로 매칭 또는 와일드카드 매칭
                    if (excluded.endsWith("/**")) {
                        String prefix = excluded.substring(0, excluded.length() - 3);
                        return requestPath.startsWith(prefix);
                    } else if (excluded.endsWith("/*")) {
                        String prefix = excluded.substring(0, excluded.length() - 2);
                        return requestPath.startsWith(prefix);
                    } else {
                        return requestPath.equals(excluded) || requestPath.startsWith(excluded + "/");
                    }
                });
    }

    /**
     * HTTP 요청에서 JWT 토큰을 추출하는 메서드
     * Authorization 헤더에서 "Bearer " 접두사 제거 후 토큰 반환
     *
     * @param request HTTP 요청 객체
     * @return 추출된 JWT 토큰 문자열, 존재하지 않으면 null 반환
     */
    private String resolveToken(HttpServletRequest request) {
        String bearer = request.getHeader("Authorization");

        if (bearer != null && bearer.startsWith("Bearer ")) {
            return bearer.substring(7);
        }
        return null;
    }
}
