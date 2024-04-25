package com.example.backend.repository.comment;

import com.example.backend.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long>, CommentRepositoryCustom {

    Comment findByCommentId(Long commentId);

}
