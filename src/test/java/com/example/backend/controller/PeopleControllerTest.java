package com.example.backend.controller;

import com.example.backend.domain.User;
import com.example.backend.repository.people.PeopleRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class PeopleControllerTest {

    @Autowired
    private EntityManager em;
    @Autowired
    private PeopleRepository peopleRepository;

//    @BeforeEach
//    void setUp() {
//    }

    @Test
    void getPeopleDetail() {
        User userA = new User("aaa", "backend", "pictures/userPicture.jpg",
                "java, spring, nodejs", 100, 300, LocalDateTime.now());

        User userB = new User("bbb", "frontend", "pictures/userPicture.jpg",
                "javascript, nextJs", 200, 600, LocalDateTime.now().minusDays(1));

        peopleRepository.save(userA);
        peopleRepository.save(userB);

        Long peopleId = userA.getUserId();
        User user = peopleRepository.findById(peopleId)
                .orElseThrow(() -> new RuntimeException("해당 피플을 찾을 수 없습니다."));

        assertThat(user.getNickname()).isEqualTo("aaa");
    }

    @Test
    void 피플정보없음() {

        Long peopleId = 99L; //존재하지 않는 peopleId

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> {
                    peopleRepository.findById(peopleId)
                            .orElseThrow(() -> new RuntimeException("해당 피플을 찾을 수 없습니다."));
                }
        );
        assertThat(exception.getMessage()).isEqualTo("해당 피플을 찾을 수 없습니다.");
    }
}