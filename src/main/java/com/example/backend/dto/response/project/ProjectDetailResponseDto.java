package com.example.backend.dto.response.project;

import com.example.backend.dto.request.project.RecruitDto;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public class ProjectDetailResponseDto {

    private Long projectId;

    private Long userId;

    private String nickname;

    private String userFileUrl;

    private String projectFileUrl;

    private String title;

    private String techStack;

    private String softSkill;

    private String importantQuestion;

    private String deadline;

    private String recruitment;

    private Boolean imploymentStatus;

    private int viewCount;

    private int favoriteCount;

    private String description;

    private LocalDateTime createdAt;

    private LocalDateTime lastModifiedAt;

    private RecruitDto recruit;

}
