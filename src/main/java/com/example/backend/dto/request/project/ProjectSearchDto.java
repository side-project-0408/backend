package com.example.backend.dto.request.project;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProjectSearchDto {

    private int page;

    private int size;

    private String techStack;

    private String position;

    private String sort;

}
