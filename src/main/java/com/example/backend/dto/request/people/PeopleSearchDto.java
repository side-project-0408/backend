package com.example.backend.dto.request.people;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class PeopleSearchDto {

    private String nickname;

    private String techSize;

    private String position;

    private int page;

    private int size;

    private String sort;

    private String keyword;

    private String sc; //sortCondition 정렬 조건

}
