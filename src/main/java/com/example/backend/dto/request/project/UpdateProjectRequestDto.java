package com.example.backend.dto.request.project;

import lombok.Getter;

import java.util.List;

@Getter
public class UpdateProjectRequestDto {

    private String title;
    private String projectFileUrl;
    private String deadline;
    private String softSkill;
    private String importantQuestion;
    private String techStack;
    private String description;
    private List<RecruitRequestDto> recruit;
}
