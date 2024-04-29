package com.example.backend.controller;

import com.example.backend.common.response.CommonApiResponse;
import com.example.backend.dto.request.FavoriteRequest;
import com.example.backend.service.FavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class FavoriteController {

    private final FavoriteService favoriteService;

    @PostMapping("/projects/favorite")
    public CommonApiResponse<?> projectFavorite(@RequestBody FavoriteRequest request) {
        return new CommonApiResponse<>("success", favoriteService.projectFavorite(request));
    }

    @DeleteMapping("/projects/favorite/{projectId}")
    private CommonApiResponse<?> projectFavoriteCancel(@PathVariable Long projectId, @RequestParam Long userId) {
        return new CommonApiResponse<>("success", favoriteService.projectFavoriteCancel(projectId, userId));
    }
/*
    @PostMapping("/users/favorite")
    public CommonApiResponse<?> userFavorite(@RequestBody FavoriteRequest request) {
        return new CommonApiResponse<>("success", favoriteService.userFavorite(request));
    }

    @DeleteMapping("/users/favorite/{favoriteId}")
    private CommonApiResponse<?> userFavoriteCancel(@PathVariable Long favoriteId, @RequestParam Long userId) {
        return new CommonApiResponse<>("success", favoriteService.userFavoriteCancel(favoriteId, userId));
    }

 */

}
