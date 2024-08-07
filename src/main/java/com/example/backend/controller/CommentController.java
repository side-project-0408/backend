package com.example.backend.controller;

import com.example.backend.common.response.CommonApiResponse;
import com.example.backend.common.response.SliceApiResponse;
import com.example.backend.dto.request.project.CommentRequestDto;
import com.example.backend.service.CommentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    // 댓글 작성
    @PostMapping("/comments/{projectId}")
    public CommonApiResponse<?> postComment(@PathVariable Long projectId, @RequestBody String content, HttpServletRequest servletRequest) {
        return new CommonApiResponse<>(OK, commentService.postComment(projectId, content, servletRequest));
    }

    // 댓글 목록 가져오기
    @GetMapping("/comments/{projectId}")
    public SliceApiResponse<?> getComments(@PathVariable Long projectId, @RequestParam int page, @RequestParam int size) {
        return commentService.getComments(projectId, page, size);
    }

    // 댓글 수정
    @PatchMapping("/comments/{projectId}")
    public CommonApiResponse<?> updateComment(@PathVariable Long projectId, @RequestBody CommentRequestDto request, HttpServletRequest servletRequest) {
        return new CommonApiResponse<>(OK, commentService.updateComment(projectId, request, servletRequest));
    }

    // 댓글 삭제
    @DeleteMapping("/comments/{projectId}")
    public CommonApiResponse<?> deleteComment(@PathVariable Long projectId, @RequestParam Long commentId, HttpServletRequest servletRequest) {
        return new CommonApiResponse<>(OK, commentService.deleteComment(projectId, commentId, servletRequest));
    }

}
