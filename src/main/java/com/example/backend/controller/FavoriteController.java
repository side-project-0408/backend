package com.example.backend.controller;

import com.example.backend.common.response.CommonApiResponse;
import com.example.backend.dto.request.FavoriteRequest;
import com.example.backend.service.FavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FavoriteController {

    private final FavoriteService favoriteService;

    @PostMapping("/projects/favorite")
    public CommonApiResponse<?> projectFavorite(@RequestBody FavoriteRequest request) {
        return new CommonApiResponse<>("success", favoriteService.projectFavorite(request));
    }

}
