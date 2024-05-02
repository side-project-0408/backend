package com.example.backend.dto.response.people;

import com.example.backend.domain.User;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Getter
public class PeopleDetailResponseDto {

    private Long userId;
    private String nickname;
    private int favoriteCount;
    private int viewCount;
    private String position;
    private String userFileUrl;
    private String year;
    private String techStack;
    private String softSkill;
    private String importantQuestion;
    private String links;
    private boolean alarmStatus;
    private String content;
    private boolean employmentStatus;
    private boolean recent;

    public PeopleDetailResponseDto(User user) {
        this.userId = user.getUserId();
        this.nickname = user.getNickname();
        this.favoriteCount = user.getFavoriteCount();
        this.viewCount = user.getViewCount();
        this.position = user.getPosition();
        this.userFileUrl = user.getUserFileUrl();
        this.year = user.getYear();
        this.techStack = user.getTechStack();
        this.softSkill = user.getSoftSkill();
        this.links = user.getLinks();
        this.alarmStatus = user.isAlarmStatus();
        this.content = user.getContent();
        this.employmentStatus = user.isEmploymentStatus();
        this.recent = isRecent(user.getCreatedAt());
    }

    public boolean isRecent(LocalDateTime createdAt) {
        return ChronoUnit.DAYS.between(createdAt, LocalDateTime.now()) <= 7 ? true : false;
    }
}
