package com.example.backend.common.config;

import com.example.backend.common.filter.TokenAuthFilter;
import com.example.backend.common.handler.OAuth2SuccessHandler;
import com.example.backend.service.CustomOauth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomOauth2UserService oAuth2UserService;

    private final OAuth2SuccessHandler oAuth2SuccessHandler;

    private final TokenAuthFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http

                .csrf((auth) -> auth.disable())

                .formLogin((auth) -> auth.disable())

                .httpBasic((auth) -> auth.disable())

                .oauth2Login((oauth2) -> oauth2
                        .userInfoEndpoint((userInfoEndpointConfig) -> userInfoEndpointConfig
                                .userService(oAuth2UserService))
                        .successHandler(oAuth2SuccessHandler))

                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/oauth2/success").permitAll()
                        .anyRequest().authenticated())

                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)

                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();

    }




}