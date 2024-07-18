package com.example.backend.dto.request.people;

import lombok.Data;

@Data
public class PeopleSearchDto {

    private String nickname;

    private String techStack;

    private String position;

    private int page;

    private int size;

    private String sort;

    private String keyword;
}
