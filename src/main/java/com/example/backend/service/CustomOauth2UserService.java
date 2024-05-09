package com.example.backend.service;

import com.example.backend.domain.User;
import com.example.backend.dto.oauth2.CustomOAuth2User;
import com.example.backend.dto.oauth2.OAuthAttributes;
import com.example.backend.repository.people.PeopleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CustomOauth2UserService extends DefaultOAuth2UserService {

    private final PeopleRepository peopleRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);

        // OAuth2 서비스 id (구글, 카카오, 네이버)
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        // OAuth2 로그인 진행 시 키가 되는 필드 값(PK)
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        // OAuth2UserService
        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());
        User user = saveOrUpdate(attributes);

        return new CustomOAuth2User(user, userNameAttributeName);


    }

    // 유저 생성 및 수정 서비스 로직
    private User saveOrUpdate(OAuthAttributes attributes){

        String nickname = attributes.getNickname();

        //TODO 프로필 사진, 이메일 null 처리 필요

        // 유저가 등록 되지 않은 상태
        if (peopleRepository.findBySocialId(Long.parseLong(attributes.getNameAttributeKey())) == null) {

            // 닉네임 중복 검증, 새로운 닉네임 부여 (1 ~ 100 사이의 정수 문자열로 더하기)
            if (peopleRepository.findByNickname(nickname) == null) {
                while (peopleRepository.findByNickname(nickname) != null) {
                    nickname += (int) (Math.random() * 100 + 1);
                }
            }

            User user = User.builder()
                    .socialId(Long.parseLong(attributes.getNameAttributeKey()))
                    .socialType(attributes.getRegistrationId())
                    .userFileUrl(attributes.getPicture())
                    .createdAt(LocalDateTime.now())
                    .nickname(nickname)
                    .build();

            peopleRepository.save(user);

            return user;

        }

        return peopleRepository.findBySocialId(Long.parseLong(attributes.getNameAttributeKey()));

    }


}
