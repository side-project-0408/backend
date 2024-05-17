package com.example.backend.common.filter;

import com.example.backend.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class TokenAuthFilter extends OncePerRequestFilter {

    public final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = jwtService.resolveToken(request);
        String requestUri = request.getRequestURI();

        if (requestUri.matches("^\\/login(?:\\/.*)?$") || requestUri.matches("^\\/oauth2(?:\\/.*)?$")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 토큰이 없을 때
        if (token == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 상태 코드 401 Unauthorized
            response.getWriter().write("Unauthorized: Access token is missing"); // 메시지 전송
        }

        if (jwtService.validToken(token)) {
            UsernamePasswordAuthenticationToken authentication = jwtService.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 상태 코드 401 Unauthorized
            response.getWriter().write("토큰 유효 기간 지남"); // 메시지 전송
        }

        filterChain.doFilter(request, response);

    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {

        String[] excludePath = {
                "/favicon.ico",
                "/error",
                "/oauth2/success",
                "/projects/**",
                "/projects/hot",
                "/peoples",
                "/peoples/**",
                "/peoples/hot"
        };

        String[] excludeGetPath = {
                "/projects",
                "/comments/**"
        };

        String path = request.getRequestURI();
        String method = request.getMethod();

        if (method.equalsIgnoreCase("GET") && Arrays.stream(excludeGetPath).anyMatch(path::startsWith))
            return Arrays.stream(excludeGetPath).anyMatch(path::startsWith);

        return Arrays.stream(excludePath).anyMatch(path::startsWith);


    }

}

