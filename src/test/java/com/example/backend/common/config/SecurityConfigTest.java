package com.example.backend.common.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest
class SecurityConfigTest {

    @Autowired
    private SecurityConfig securityConfig;
    
    @Autowired
    private CorsConfigurationSource corsConfigurationSource;

    @Test
    void OriginTest() {
        // CORS 설정 가져오기
        CorsConfiguration corsConfiguration = ((UrlBasedCorsConfigurationSource) corsConfigurationSource).getCorsConfigurations().get("/**");

        // Origin 체크
        List<String> expectedAllowedOrigins = Arrays.asList("http://*");
        // 실제 CORS 설정에서 가져온 Origin과 예상 리스트를 비교
        assertEquals(expectedAllowedOrigins, corsConfiguration.getAllowedOrigins());

        // 메서드 체크
        List<String> expectedAllowedMethods = Arrays.asList("HEAD", "GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS");
        // 실제 CORS 설정에서 가져온 메서드 리스트와 예상 리스트 비교
        assertEquals(expectedAllowedMethods, corsConfiguration.getAllowedMethods());

        // 허용 credentials 체크
        assertTrue(corsConfiguration.getAllowCredentials());

        // 허용 headers 체크
        List<String> expectedAllowedHeaders = Arrays.asList("Authorization", "Authorization-refresh", "Cache-Control", "Content-Type");
        assertEquals(expectedAllowedHeaders, corsConfiguration.getAllowedHeaders());
    }
}