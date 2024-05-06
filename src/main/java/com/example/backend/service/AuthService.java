package com.example.backend.service;

import com.example.backend.common.api.KakaoApi;
import com.example.backend.domain.User;
import com.example.backend.dto.kakao.KakaoUserInfoResponse;
import com.example.backend.repository.people.PeopleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {

    private final KakaoApi kakaoApi;

    private final PeopleRepository peopleRepository;

    public String login(String code) {

        String accessToken = kakaoApi.getAccessToken(code);
        KakaoUserInfoResponse response = kakaoApi.getMemberInfo(accessToken);
        String nickname = response.getKakaoAccount().getProfile().getNickname();
        String userFileUrl = Optional.ofNullable(response.getKakaoAccount().getProfile().getProfileImageUrl()).orElse("기본 경로 이미지");

        // 유저가 등록 되지 않은 상태
        if(peopleRepository.findBySocialId(response.getId()) == null) {

            // 닉네임 중복 검증, 새로운 닉네임 부여 (1 ~ 100 사이의 정수 문자열로 더하기)
            if (peopleRepository.findByNickname(nickname) == null) {
                while (peopleRepository.findByNickname(nickname) == null){
                    nickname += (int)(Math.random() * 100 + 1);
                }
            }

            peopleRepository.save(
                    User.builder()
                            .socialId(response.getId())
                            .userFileUrl(userFileUrl)
                            .createdAt(LocalDateTime.now())
                            .nickname(nickname)
                            .build());

        }

        //TODO jwt accessToken, refreshToken 발급 받아 반환

        return "jwtToken";
    }

}
