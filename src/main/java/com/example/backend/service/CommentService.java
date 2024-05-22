package com.example.backend.service;

import com.example.backend.domain.Comment;
import com.example.backend.domain.Project;
import com.example.backend.domain.User;
import com.example.backend.dto.request.project.CommentRequestDto;
import com.example.backend.dto.response.comment.CommentResponseDto;
import com.example.backend.repository.comment.CommentRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    private final JwtService jwtService;

    // 댓글 저장
    public String postComment(Long projectId, String content, HttpServletRequest servletRequest) {

        commentRepository.save(Comment.builder()
                .user(User.builder().userId(jwtService.getUserIdFromToken(servletRequest)).build())
                .project(Project.builder().projectId(projectId).build())
                .content(content)
                .createdAt(LocalDateTime.now())
                .build());

        return "댓글 저장 완료";

    }

    // 댓글 목록 가져오기
    public Slice<CommentResponseDto> getComments(Long projectId, int page, int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").ascending());

        return commentRepository.findSliceByProjectId(projectId, pageable);

    }

    // 댓글 수정
    public String updateComment(Long projectId, CommentRequestDto request, HttpServletRequest servletRequest) {

        Comment comment = commentRepository.findByCommentId(request.getCommentId());

        if (!(comment.getProject().getProjectId().equals(projectId) && comment.getUser().getUserId().equals(jwtService.getUserIdFromToken(servletRequest))))
            throw new RuntimeException("프로젝트 번호와 댓글 작성자가 일치하지 않습니다.");

        comment.updateContent(request.getContent());
        comment.updateLastModifiedAt(LocalDateTime.now());

        return "댓글 수정 완료";

    }

    // 댓글 삭제
    public String deleteComment(Long projectId, Long commentId, HttpServletRequest servletRequest) {

        Comment comment = commentRepository.findByCommentId(commentId);

        if (!(comment.getProject().getProjectId().equals(projectId) && comment.getUser().getUserId().equals(jwtService.getUserIdFromToken(servletRequest))))
            throw new RuntimeException("프로젝트 번호와 댓글 작성자가 일치하지 않습니다.");

        commentRepository.delete(comment);

        return "댓글 삭제 완료";

    }

}
