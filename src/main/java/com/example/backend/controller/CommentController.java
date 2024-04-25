package com.example.backend.controller;

import com.example.backend.common.response.CommonApiResponse;
import com.example.backend.dto.request.project.CommentRequestDto;
import com.example.backend.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    @PostMapping("/comments/{projectId}")
    public CommonApiResponse<?> postComment(@PathVariable Long projectId, @RequestBody CommentRequestDto request) {
        return new CommonApiResponse<>("success", commentService.postComment(projectId, request));
    }

    @PatchMapping("/comments/{projectId}")
    public CommonApiResponse<?> updateComment(@PathVariable Long projectId, @RequestBody CommentRequestDto request) {
        return new CommonApiResponse<>("success", commentService.updateComment(projectId, request));
    }

    @DeleteMapping("/comments/{projectId}")
    public CommonApiResponse<?> deleteComment(@PathVariable Long projectId, @RequestParam Long userId, @RequestParam Long commentId) {
        return new CommonApiResponse<>("success", commentService.deleteComment(projectId, userId, commentId));
    }

}
