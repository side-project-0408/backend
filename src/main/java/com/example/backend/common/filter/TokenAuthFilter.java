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
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class TokenAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    private final AntPathMatcher pathMatcher = new AntPathMatcher();


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
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 상태 코드 401 Unauthorized
            response.getWriter().write("토큰 유효 기간 지남"); // 메시지 전송
        }

        filterChain.doFilter(request, response);

    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {

        String[] excludePaths = {
                "/favicon.ico",
                "/error",
                "/oauth2/success",
        };

        String[] excludeGetPaths = {
                "/projects",
                "/projects/**",
                //"/projects/hot",
                "/comments/**",
                "/peoples",
                "/peoples/**",
                //"/peoples/hot",
                "/users/nickname"
        };

        String path = request.getRequestURI();
        String method = request.getMethod();

        // GET 메소드에 대한 경로 제외 확인
        if (method.equalsIgnoreCase("GET")) {
            for (String exclude : excludeGetPaths) {
                if (pathMatcher.match(exclude, path)) {
                    return true;
                }
            }
        }

        // 다른 메소드에 대한 경로 제외 확인
        for (String exclude : excludePaths) {
            if (pathMatcher.match(exclude, path)) {
                return true;
            }
        }

        return false;

    }

}

