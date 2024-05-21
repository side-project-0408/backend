package com.example.backend.controller;

import com.example.backend.domain.Project;
import com.example.backend.domain.Recruit;
import com.example.backend.domain.User;
import com.example.backend.dto.request.people.UpdateUserRequestDto;
import com.example.backend.repository.people.PeopleRepository;
import com.example.backend.repository.project.ProjectRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MyPageControllerTest {

    @Autowired
    private EntityManager em;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private PeopleRepository peopleRepository;

    @Test
    void deletePost() {
        List<Recruit> recruits = new ArrayList<>();
        recruits.add(Recruit.builder().position("backend").currentCount(2).targetCount(3).build());
        recruits.add(Recruit.builder().position("frontend").currentCount(4).targetCount(5).build());
        User userA = new User("aaa", "backend", "pictures/userPicture.jpg",
                "java, spring, nodejs", 100, 300, LocalDateTime.now());

        Project project = Project.builder()
                .user(userA)
                .title("제목")
                .projectFileUrl("user.jpg")
                .deadline(LocalDate.now())
                .softSkill("시간 관리, 직업 윤리")
                .importantQuestion("주 1회 회의, 시간 관리")
                .description("프로젝트 내용")
                .recruits(recruits)
                .build();

        projectRepository.save(project);

        Project findProject = projectRepository.findByProjectId(project.getProjectId());

        Project result = projectRepository.findByUserUserIdAndProjectId(findProject.getUser().getUserId(), findProject.getProjectId());
        if(project == null) {
            throw new RuntimeException("해당 프로젝트는 존재하지 않습니다.");
        }
        projectRepository.delete(result);

        assertThat(result.getProjectId()).isEqualTo(1);
    }

    @Test
    void editUser() {
        User userA = new User("aaa", "backend", "pictures/userPicture.jpg",
                "java, spring, nodejs", 100, 300, LocalDateTime.now());

        User userB = new User("bbb", "frontend", "pictures/userPicture.jpg",
                "javascript, nextJs", 200, 600, LocalDateTime.now().minusDays(1));


        peopleRepository.save(userA);
        peopleRepository.save(userB);

        Long userId = userA.getUserId();
        User user = peopleRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("해당 사용자는 존재하지 않습니다."));

        user.updateUser("파이리",
                "frontend",
                "http://수정파일",
                true,
                "java,spring",
                "시간 약속 잘 지킴",
                "1",
                "블로그 링크",
                "프로젝트 구인 중",
                false, "aaa@gmail.com");

        peopleRepository.save(user);

        assertThat(user.getNickname()).isEqualTo("파이리");
        assertThat(user.getPosition()).isEqualTo("frontend");
        assertThat(user.getLinks()).isEqualTo("블로그 링크");
    }

}