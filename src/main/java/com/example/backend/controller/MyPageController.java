package com.example.backend.controller;

import com.example.backend.common.response.CommonApiResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class MyPageController {

    //마이페이지 내 정보 조회
    @GetMapping("/users/{userId}")
    public CommonApiResponse<?> getUser(@PathVariable("userId") Long userId) {
        return new CommonApiResponse<>("success", null);
    }

    //마이페이지 내 정보 수정
    @PatchMapping("/users/{userId}")
    public CommonApiResponse<?> editUser(@PathVariable("userId") Long userId) {
        return new CommonApiResponse<>("success", null);
    }

    //내가 작성한 프로젝트 목록 확인
    @GetMapping("/posts/{userId}")
    public CommonApiResponse<?> getPosts(@PathVariable("userId") Long userId,
                                         @PathVariable("projectId") Long projectId) {

        return new CommonApiResponse<>("success", null);
    }

    //내가 작성한 프로젝트 수정
    @PatchMapping("/posts/{userId}")
    public CommonApiResponse<?> editPost(@PathVariable("userId") Long userId,
                                         @PathVariable("projectId") Long projectId) {

        return new CommonApiResponse<>("success", null);
    }

    //내가 작성한 프로젝트 삭제
    @DeleteMapping("/posts/{userId}")
    public CommonApiResponse<?> deletePost(@PathVariable("userId") Long userId,
                                         @PathVariable("projectId") Long projectId) {

        return new CommonApiResponse<>("success", null);
    }
}
