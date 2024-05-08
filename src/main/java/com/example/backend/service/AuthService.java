package com.example.backend.service;

import com.example.backend.common.api.KakaoApi;
import com.example.backend.common.provider.JwtProvider;
import com.example.backend.domain.User;
import com.example.backend.dto.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {

    private final KakaoApi kakaoApi;

    private final JwtProvider jwtProvider;

    public Map<String, String> login(String code) {

        User user = kakaoApi.signUpAndGetUser(kakaoApi.getAccessToken(code));
        CustomUserDetails customUserDetails = new CustomUserDetails(user);
        Map<String, String> tokens = new HashMap<>();

        String accessToken = jwtProvider.createAccessToken(customUserDetails);
        String refreshToken = jwtProvider.createRefreshToken(customUserDetails);

        tokens.put("accessToken", accessToken);
        tokens.put("refreshToken", refreshToken);

        return tokens;

    }

}
