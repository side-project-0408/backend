package com.example.backend.dto.response.people;

import com.example.backend.domain.User;

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
        this.importantQuestion = user.getImportantQuestion();
        this.links = user.getLinks();
        this.alarmStatus = user.isAlarmStatus();
        this.content = user.getContent();
    }
}
