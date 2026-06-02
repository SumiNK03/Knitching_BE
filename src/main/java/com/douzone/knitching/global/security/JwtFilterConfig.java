package com.douzone.knitching.global.security;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * JWT 필터 등록 설정 클래스
 * JWT 필터를 Spring Bean으로 등록합니다
 */
@Configuration
public class JwtFilterConfig {

    /**
     * JWT 필터를 Spring Bean으로 등록
     * 필터 제외 경로는 JwtFilter 클래스의 EXCLUDED_PATHS에서 관리됩니다
     *
     * @param jwtFilter JWT 필터 빈
     * @return 필터 등록 빈
     */
    @Bean
    public FilterRegistrationBean<JwtFilter> jwtFilterRegistrationBean(JwtFilter jwtFilter) {
        FilterRegistrationBean<JwtFilter> registrationBean = new FilterRegistrationBean<>(jwtFilter);
        
        // 필터가 적용될 URL 패턴 (모든 경로)
        registrationBean.addUrlPatterns("/*");
        
        // 필터 순서 설정 (낮을수록 먼저 실행)
        registrationBean.setOrder(1);
        
        return registrationBean;
    }
}

