package com.example.backend.dto.request.project;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RecruitRequestDto {

    private String position;

    private int currentCount;

    private int targetCount;

}
