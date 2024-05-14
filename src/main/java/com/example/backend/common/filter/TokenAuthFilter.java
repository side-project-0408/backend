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

@Component
@RequiredArgsConstructor
public class TokenAuthFilter extends OncePerRequestFilter {

    public final JwtService jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = jwtProvider.resolveToken(request);
        String requestUri = request.getRequestURI();

        if (requestUri.matches("^\\/login(?:\\/.*)?$") || requestUri.matches("^\\/oauth2(?:\\/.*)?$")) {
            filterChain.doFilter(request, response);
            return;
        }

        System.out.println(token);
        // 토큰이 없을 때
        if (token == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 상태 코드 401 Unauthorized
            response.getWriter().write("Unauthorized: Access token is missing"); // 메시지 전송
        }

        if (jwtProvider.validToken(token)) {
            UsernamePasswordAuthenticationToken authentication = jwtProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 상태 코드 401 Unauthorized
            response.getWriter().write("토큰 유효 기간 지남"); // 메시지 전송
        }

        System.out.println("doFilter");
        filterChain.doFilter(request, response);

    }

}

