package com.example.backend.dto.response.people;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Data
public class PeopleResponseDto {

    private String nickname;

    private int favoriteCount;

    private int viewCount;

    private String position;

    private String userFileUrl;

    private String techStack;

    private String softSkill;

    private Long userId;

    private boolean recent;

    private LocalDateTime createdAt;

    @QueryProjection
    public PeopleResponseDto(String nickname, int favoriteCount, int viewCount, String position, String userFileUrl,
                             String techStack, String softSkill, Long userId, LocalDateTime createdAt) {
        this.nickname = nickname;
        this.favoriteCount = favoriteCount;
        this.viewCount = viewCount;
        this.position = position;
        this.userFileUrl = userFileUrl;
        this.techStack = techStack;
        this.softSkill = softSkill;
        this.userId = userId;
        this.recent = isRecent(createdAt);
    }

    public boolean isRecent(LocalDateTime createdAt) {
        return ChronoUnit.DAYS.between(createdAt, LocalDateTime.now()) <= 7 ? true : false;
    }
}
