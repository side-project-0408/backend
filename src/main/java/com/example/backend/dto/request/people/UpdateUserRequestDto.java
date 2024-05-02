package com.example.backend.dto.request.people;

import lombok.Data;
import lombok.Getter;

@Data
public class UpdateUserRequestDto {

    private String nickname;
    private String userFileUrl;
    private String techStack;
    private String position;
    private boolean employmentStatus;
    private String year;
    private String links;
    private boolean alarmStatus;
    private String content;
    private String softSkill;
    private String importantQuestion;

}
