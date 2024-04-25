package com.example.backend.dto.request.project;

import lombok.Getter;

@Getter
public class ProjectSearchDto {

    private int page;

    private int size;

    private String sort;

    private String techStack;

    private String position;

}
