package com.example.backend.service;

import com.example.backend.domain.Comment;
import com.example.backend.domain.Project;
import com.example.backend.domain.User;
import com.example.backend.dto.request.project.CommentRequestDto;
import com.example.backend.dto.response.comment.CommentResponseDto;
import com.example.backend.repository.comment.CommentRepository;
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

    // 댓글 저장
    public String postComment(Long projectId, CommentRequestDto request) {

        commentRepository.save(Comment.builder()
                .user(User.builder().userId(request.getUserId()).build())
                .project(Project.builder().projectId(projectId).build())
                .content(request.getContent())
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
    public String updateComment(Long projectId, CommentRequestDto request) {

        Comment comment = commentRepository.findByCommentId(request.getCommentId());

        comment.updateContent(request.getContent());
        comment.updateLastModifiedAt(LocalDateTime.now());

        return "댓글 수정 완료";

    }

    // 댓글 삭제
    public String deleteComment(Long projectId, Long userId, Long commentId) {

        Comment comment = commentRepository.findByCommentId(commentId);

        if (comment.getProject().getProjectId().equals(projectId) && comment.getUser().getUserId().equals(userId)) commentRepository.delete(comment);
        //TODO 예외 처리 해야함
        else {
            try {
                throw new Exception("예외 발생");
            } catch (Exception e){
                e.printStackTrace();
            }
        }

        return "댓글 삭제 완료";

    }

}
