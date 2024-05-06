package com.example.backend.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

public class KakaoMessageService {

    private static final String KAKAO_API_URL =  "https://kapi.kakao.com/v2/api/talk/channels";

    public String sendKakaoMessage(String accessToken, String messageText, String projectUrl) throws Exception {
        //HTTP 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer" + accessToken);
        headers.set("Content-Type", "application/json");

        //JSON 데이터 생성
        Map<String, Object> message = new HashMap<>();
        message.put("object_type", "text");
        message.put("text", messageText); // 메시지 내용

        // 링크 정보 추가
        if (projectUrl != null) {
            Map<String, String> link = new HashMap<>();
            link.put("web_url", projectUrl); // 웹 URL
            link.put("mobile_url", projectUrl); // 모바일 URL (동일하게 설정)
            message.put("link", link);
        }

        //JSON 문자열로 변환
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonBody = objectMapper.writeValueAsString(message);

        // HTTP POST요청
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.postForEntity(KAKAO_API_URL, jsonBody, String.class, headers);

        if (response.getStatusCode() == HttpStatus.OK) {
            return "제안 신청이 성공하였습니다.";
        } else {
            return "제안 신청에 실패했습니다." + response.getStatusCode();
        }
    }
}
