package com.example.backend.controller;

import com.example.backend.common.response.CommonApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class AuthController {

    @GetMapping("auth/success")
    public CommonApiResponse<?> oAuth2Success(@RequestParam("accessToken") String accessToken,
                                                             @RequestParam("refreshToken") String refreshToken) {
        Map<String, String> tokens = new HashMap<>();
        tokens.put("accessToken", accessToken);
        tokens.put("refreshToken", refreshToken);
        return new CommonApiResponse<>("success", tokens);
    }


}
