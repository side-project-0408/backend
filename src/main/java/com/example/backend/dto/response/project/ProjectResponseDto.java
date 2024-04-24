package com.example.backend.dto.response.project;

import com.example.backend.dto.request.project.RecruitDto;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public class ProjectResponseDto {

    private Long projectId;

    private String nickname;

    private String userFileUrl;

    private String title;

    private String techStack;

    private LocalDate deadline;

    private int viewCount;

    private int favoriteCount;

    private Boolean recent;

    private RecruitDto recruit;

}
