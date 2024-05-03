package com.example.backend.repository;

import com.example.backend.domain.User;
import com.example.backend.dto.request.people.PeopleSearchDto;
import com.example.backend.dto.response.people.PeopleResponseDto;
import com.example.backend.repository.people.PeopleRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@Transactional
class PeopleRepositoryImplTest {

    @Autowired
    EntityManager em;

    @Autowired
    private PeopleRepository peopleRepository;

    @Test
    void findPeoples() {

        User userA = new User("aaa", "backend", "pictures/userPicture.jpg",
                "java, spring, nodejs", 100, 300, LocalDateTime.now());

        User userB = new User("bbb", "frontend", "pictures/userPicture.jpg",
                "javascript, nextjs", 200, 600, LocalDateTime.now().minusDays(1));

        peopleRepository.save(userA);
        peopleRepository.save(userB);

        PeopleSearchDto dto = new PeopleSearchDto();
        dto.setTechSize("nextjs");
        //dto.setKeyword("aaa");

        List<PeopleResponseDto> result = peopleRepository.findPeoples(dto);

        assertThat(result.size()).isEqualTo(1);
    }

    //내가 찜한 사람 목록 확인
    @Test
    void findFavoritePeoples() {
        User userA = new User("aaa", "backend", "pictures/userPicture.jpg",
                "java, spring, nodejs", 100, 300, LocalDateTime.now());

        User userB = new User("bbb", "frontend", "pictures/userPicture.jpg",
                "javascript, nextJs", 200, 600, LocalDateTime.now().minusDays(1));

        //userLike 에 값 추가
        Set<Long> userLikeSet = new HashSet<>();
        userLikeSet.add(1L);
        userLikeSet.add(2L);

        userA.setUserLike(userLikeSet);

        peopleRepository.save(userA);
        peopleRepository.save(userB);

        assertThat(userA.getUserLike()).contains(1L);
    }
}