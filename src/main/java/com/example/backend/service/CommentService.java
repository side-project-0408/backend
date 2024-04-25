package com.example.backend.service;

import com.example.backend.domain.Comment;
import com.example.backend.domain.Project;
import com.example.backend.domain.User;
import com.example.backend.dto.request.project.CommentRequestDto;
import com.example.backend.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    public String postComment(Long projectId, CommentRequestDto request) {

        commentRepository.save(Comment.builder()
                .user(User.builder().userId(request.getUserId()).build())
                .project(Project.builder().projectId(projectId).build())
                .content(request.getContent())
                .createdAt(LocalDateTime.now())
                .build());

        return "Comment creation completed";

    };

    public String updateComment(Long projectId, CommentRequestDto request) {

        Comment comment = commentRepository.findByCommentId(request.getCommentId());

        comment.updateContent(request.getContent());
        comment.updateLastModifiedAt(LocalDateTime.now());

        return "Comment update completed";

    }

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

        return "Comment delete completed";

    }
}
