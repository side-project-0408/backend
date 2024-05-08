package com.example.backend.controller;

import com.example.backend.common.response.CommonApiResponse;
import com.example.backend.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @GetMapping("/auth/kakao/callback")
    public CommonApiResponse<?> kakaoLogin(@RequestParam String code) {
        return new CommonApiResponse<>("success", authService.login(code));
    }


}
