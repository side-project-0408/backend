package com.example.backend.controller;

import com.example.backend.common.response.CommonApiResponse;
import com.example.backend.service.FavoriteService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class FavoriteController {

    private final FavoriteService favoriteService;

    // 프로젝트 찜하기
    @PostMapping("/projects/favorite")
    public CommonApiResponse<?> projectFavorite(@RequestParam Long projectId, HttpServletRequest servletRequest) {
        return new CommonApiResponse<>("success", favoriteService.projectFavorite(projectId, servletRequest));
    }

    // 프로젝트 찜하기 취소
    @DeleteMapping("/projects/favorite")
    private CommonApiResponse<?> projectFavoriteCancel(@RequestParam Long projectId, HttpServletRequest servletRequest) {
        return new CommonApiResponse<>("success", favoriteService.projectFavoriteCancel(projectId, servletRequest));
    }

    // 유저 찜하기
    @PostMapping("/users/favorite")
    public CommonApiResponse<?> userFavorite(@RequestParam Long favoriteId, HttpServletRequest servletRequest) {
        return new CommonApiResponse<>("success", favoriteService.userFavorite(favoriteId, servletRequest));
    }

    // 유저 찜하기 취소
    @DeleteMapping("/users/favorite")
    private CommonApiResponse<?> userFavoriteCancel(@RequestParam Long favoriteId, HttpServletRequest servletRequest) {
        return new CommonApiResponse<>("success", favoriteService.userFavoriteCancel(favoriteId, servletRequest));
    }

}
