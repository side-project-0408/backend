package com.example.backend;

import com.example.backend.common.provider.JwtProvider;
import com.example.backend.domain.User;
import com.example.backend.dto.security.CustomUserDetails;
import com.example.backend.repository.people.PeopleRepository;
import com.example.backend.service.ProjectService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
@Transactional
@Rollback
public class ProjectControllerTest {

    @Autowired
    ProjectService projectService;

    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    PeopleRepository peopleRepository;

    @Test
    public void jwtTest() {

        User user = peopleRepository.findUserByUserId(1L);

        CustomUserDetails customUserDetails = new CustomUserDetails(user);
        Map<String, String> tokens = new HashMap<>();

        String accessToken = jwtProvider.createAccessToken(customUserDetails);
        String refreshToken = jwtProvider.createRefreshToken(customUserDetails);

        System.out.println(accessToken);
        System.out.println("==============");
        System.out.println(refreshToken);



    }

}
