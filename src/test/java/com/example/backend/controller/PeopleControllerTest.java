package com.example.backend.controller;

import com.example.backend.domain.User;
import com.example.backend.dto.response.people.PeopleDetailResponseDto;
import com.example.backend.repository.PeopleRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class PeopleControllerTest {

    @Autowired
    private EntityManager em;
    @Autowired
    private PeopleRepository peopleRepository;

    @Test
    void getPeopleDetail() {
        User userA = new User(1L, "aaa", "backend", "pictures/userPicture.jpg",
                "java, spring, nodejs", 100, 300, LocalDateTime.now());

        User userB = new User(2L, "bbb", "frontend", "pictures/userPicture.jpg",
                "javascript, nextJs", 200, 600, LocalDateTime.now().minusDays(1));

        peopleRepository.save(userA);
        peopleRepository.save(userB);

        Long peopleId = 1L;

        User user = peopleRepository.findById(peopleId)
                .orElseThrow(() -> new RuntimeException("해당 피플을 찾을 수 없습니다."));

        assertThat(user.getNickname()).isEqualTo("aaa");
    }
}