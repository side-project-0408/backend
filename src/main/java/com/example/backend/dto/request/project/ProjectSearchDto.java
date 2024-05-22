package com.example.backend.dto.request.project;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProjectSearchDto {

    private int page;

    private int size;

    private String techStack;

    private String position;

    private String sort;

    private String keyword;

}
