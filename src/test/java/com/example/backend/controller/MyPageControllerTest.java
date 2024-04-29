package com.example.backend.controller;

import com.example.backend.domain.Project;
import com.example.backend.domain.Recruit;
import com.example.backend.domain.User;
import com.example.backend.repository.project.ProjectRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class MyPageControllerTest {

    @Autowired
    private ProjectRepository projectRepository;

    @Test
    void deletePost() {
        List<Recruit> recruits = new ArrayList<>();
        recruits.add(Recruit.builder().position("backend").currentCount(2).targetCount(3).build());
        recruits.add(Recruit.builder().position("frontend").currentCount(4).targetCount(5).build());
        User user = User.builder().nickname("AAA").build();
        Project project = Project.builder()
                .user(user)
                .title("제목")
                .projectFileUrl("user.jpg")
                .deadline(LocalDate.now())
                .softSkill("시간 관리, 직업 윤리")
                .importantQuestion("주 1회 회의, 시간 관리")
                .description("프로젝트 내용")
                .recruits(recruits)
                .build();

        Project result = projectRepository.save(project);


        assertThat(project.getProjectId()).isEqualTo(1);
    }
}