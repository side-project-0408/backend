package com.example.backend.common.config;

import com.example.backend.common.filter.TokenAuthFilter;
import com.example.backend.common.handler.OAuth2SuccessHandler;
import com.example.backend.service.CustomOauth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomOauth2UserService oAuth2UserService;

    private final OAuth2SuccessHandler oAuth2SuccessHandler;

    private final TokenAuthFilter jwtAuthFilter;

    private final CorsConfig config;

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
                        .requestMatchers(
                                new AntPathRequestMatcher("/oauth2/success"),
                                new AntPathRequestMatcher("/projects", HttpMethod.GET.toString()),
                                new AntPathRequestMatcher("/comments/**", HttpMethod.GET.toString()),
                                new AntPathRequestMatcher("/projects/**"),
                                new AntPathRequestMatcher("/projects/hot"),
                                new AntPathRequestMatcher("/peoples"),
                                new AntPathRequestMatcher("/peoples/**"),
                                new AntPathRequestMatcher("/peoples/hot")
                        ).permitAll()
                        .anyRequest().authenticated())

                .addFilter(config.corsFilter())

                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)

                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();

    }

}