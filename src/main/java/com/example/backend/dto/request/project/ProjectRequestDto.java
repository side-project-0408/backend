package com.example.backend.dto.request.project;

import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@Getter
public class ProjectRequestDto {

    private String title;

    private String projectFileUrl;

    private LocalDate deadline;

    private String techStack;

    private String softSkill;

    private String importantQuestion;

    private String description;

    private List<RecruitRequestDto> recruit;

    private MultipartFile file;

}
