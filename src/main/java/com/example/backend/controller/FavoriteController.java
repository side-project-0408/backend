package com.example.backend.controller;

import com.example.backend.common.response.CommonApiResponse;
import com.example.backend.service.FavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequiredArgsConstructor
public class FavoriteController {

    private final FavoriteService favoriteService;

    // 프로젝트 찜하기
    @PostMapping("/projects/favorite")
    public CommonApiResponse<?> projectFavorite(@RequestParam Long projectId, Authentication authentication) {
        return new CommonApiResponse<>(OK, favoriteService.projectFavorite(projectId, authentication));
    }

    // 프로젝트 찜하기 취소
    @DeleteMapping("/projects/favorite")
    private CommonApiResponse<?> projectFavoriteCancel(@RequestParam Long projectId, Authentication authentication) {
        return new CommonApiResponse<>(OK, favoriteService.projectFavoriteCancel(projectId, authentication));
    }

    // 유저 찜하기
    @PostMapping("/users/favorite")
    public CommonApiResponse<?> userFavorite(@RequestParam Long favoriteId, Authentication authentication) {
        return new CommonApiResponse<>(OK, favoriteService.userFavorite(favoriteId, authentication));
    }

    // 유저 찜하기 취소
    @DeleteMapping("/users/favorite")
    private CommonApiResponse<?> userFavoriteCancel(@RequestParam Long favoriteId, Authentication authentication) {
        return new CommonApiResponse<>(OK, favoriteService.userFavoriteCancel(favoriteId, authentication));
    }

}
