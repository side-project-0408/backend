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

    // 프로젝트 찜하기
    @PostMapping("/projects/favorite")
    public CommonApiResponse<?> projectFavorite(@RequestBody FavoriteRequest request) {
        return new CommonApiResponse<>("success", favoriteService.projectFavorite(request));
    }

    // 프로젝트 찜하기 취소
    @DeleteMapping("/projects/favorite/{projectId}")
    private CommonApiResponse<?> projectFavoriteCancel(@PathVariable Long projectId, @RequestParam Long userId) {
        return new CommonApiResponse<>("success", favoriteService.projectFavoriteCancel(projectId, userId));
    }

    // 유저 찜하기
    @PostMapping("/users/favorite")
    public CommonApiResponse<?> userFavorite(@RequestBody FavoriteRequest request) {
        return new CommonApiResponse<>("success", favoriteService.userFavorite(request));
    }

    // 유저 찜하기 취소
    @DeleteMapping("/users/favorite/{favoriteId}")
    private CommonApiResponse<?> userFavoriteCancel(@PathVariable Long favoriteId, @RequestParam Long userId) {
        return new CommonApiResponse<>("success", favoriteService.userFavoriteCancel(favoriteId, userId));
    }

}
