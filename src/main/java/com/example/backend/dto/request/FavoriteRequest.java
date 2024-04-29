package com.example.backend.dto.request;

import lombok.Getter;

@Getter
public class FavoriteRequest {

    private Long projectId;

    private Long userId;

    private Long favoriteId;

}
