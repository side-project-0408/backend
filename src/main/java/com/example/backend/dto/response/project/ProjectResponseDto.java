package com.example.backend.dto.response.project;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
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

    @QueryProjection
    public ProjectResponseDto(Long projectId, String nickname, String userFileUrl, String title, String techStack, LocalDate deadline, int viewCount, int favoriteCount) {
        this.projectId = projectId;
        this.nickname = nickname;
        this.userFileUrl = userFileUrl;
        this.title = title;
        this.techStack = techStack;
        this.deadline = deadline;
        this.viewCount = viewCount;
        this.favoriteCount = favoriteCount;
    }

}
