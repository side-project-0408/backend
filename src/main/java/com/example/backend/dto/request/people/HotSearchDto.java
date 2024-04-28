package com.example.backend.dto.request.people;

import lombok.Getter;

@Getter
public class HotSearchDto {
    private int page;

    private int size;

    private String sort;
}
