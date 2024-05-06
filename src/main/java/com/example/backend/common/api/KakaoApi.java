package com.example.backend.common.api;

import com.example.backend.dto.kakao.KakaoTokenResponse;
import com.example.backend.dto.kakao.KakaoUserInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class KakaoApi {

    private final RestTemplate restTemplate;

    @Value("${kakao.client-id}")
    private String clientId;

    @Value("{kakao.rest-api-key}")
    private String restApiKey;

    public String getAccessToken(String code) {

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", clientId);
        body.add("redirect_uri", "http://localhost:8081/auth/kakao/callback");
        body.add("code", code);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, httpHeaders);

        KakaoTokenResponse response = restTemplate.postForEntity(
                "https://kauth.kakao.com/oauth/token",
                request,
                KakaoTokenResponse.class
        ).getBody();

        return response.getAccessToken();

    }

    public KakaoUserInfoResponse getMemberInfo(String accessToken) {

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<Void> request = new HttpEntity<>(headers);

        KakaoUserInfoResponse response = restTemplate.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.GET,
                request,
                KakaoUserInfoResponse.class
        ).getBody();

        return response;

    }

}
