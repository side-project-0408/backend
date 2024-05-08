package com.example.backend.common.api;

import com.example.backend.domain.User;
import com.example.backend.dto.kakao.KakaoTokenResponse;
import com.example.backend.dto.kakao.KakaoUserInfoResponse;
import com.example.backend.repository.people.PeopleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
@Transactional
@RequiredArgsConstructor
public class KakaoApi {

    private final RestTemplate restTemplate;
    private final PeopleRepository peopleRepository;

    @Value("${kakao.client-id}")
    private String clientId;

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

    public KakaoUserInfoResponse getUserInfo(String accessToken) {

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

    // 회원가입 체크 후 유저 반환
    public User signUpAndGetUser(String accessToken) {

        KakaoUserInfoResponse userInfo = getUserInfo(accessToken);
        String nickname = userInfo.getKakaoAccount().getProfile().getNickname();
        String userFileUrl = Optional.ofNullable(userInfo.getKakaoAccount().getProfile().getProfileImageUrl()).orElse("기본 경로 이미지");

        // 유저가 등록 되지 않은 상태
        if (peopleRepository.findBySocialId(userInfo.getId()) == null) {

            // 닉네임 중복 검증, 새로운 닉네임 부여 (1 ~ 100 사이의 정수 문자열로 더하기)
            if (peopleRepository.findByNickname(nickname) == null) {
                while (peopleRepository.findByNickname(nickname) != null) {
                    nickname += (int) (Math.random() * 100 + 1);
                }
            }

            User user = User.builder()
                    .socialId(userInfo.getId())
                    .socialType("KAKAO")
                    .userFileUrl(userFileUrl)
                    .createdAt(LocalDateTime.now())
                    .nickname(nickname)
                    .build();

            peopleRepository.save(user);

            return user;

        }

        return peopleRepository.findBySocialId(userInfo.getId());

    }

}

