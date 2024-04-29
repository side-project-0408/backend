package com.example.backend.dto.response.project;

import com.example.backend.dto.request.project.RecruitRequestDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
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

    private LocalDate deadline;

    private String recruitment;

    private Boolean employmentStatus;

    private int viewCount;

    private int favoriteCount;

    private String description;

    private LocalDateTime createdAt;

    private LocalDateTime lastModifiedAt;

    private List<RecruitRequestDto> recruit;

}
