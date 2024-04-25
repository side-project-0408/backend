package com.example.backend.dto.response.people;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class PeopleResponseDto {

    private String nickname;

    private int favoriteCount;

    private int viewCount;

    private String position;

    private String userFileUrl;

    @QueryProjection
    public PeopleResponseDto(String nickname, int favoriteCount, int viewCount, String position, String userFileUrl) {
        this.nickname = nickname;
        this.favoriteCount = favoriteCount;
        this.viewCount = viewCount;
        this.position = position;
        this.userFileUrl = userFileUrl;
    }
}
