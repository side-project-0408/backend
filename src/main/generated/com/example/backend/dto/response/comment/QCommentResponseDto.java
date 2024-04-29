package com.example.backend.dto.response.comment;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.example.backend.dto.response.comment.QCommentResponseDto is a Querydsl Projection type for CommentResponseDto
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QCommentResponseDto extends ConstructorExpression<CommentResponseDto> {

    private static final long serialVersionUID = 73940993L;

    public QCommentResponseDto(com.querydsl.core.types.Expression<Long> commentId, com.querydsl.core.types.Expression<Long> userId, com.querydsl.core.types.Expression<String> nickname, com.querydsl.core.types.Expression<String> fileUrl, com.querydsl.core.types.Expression<String> content, com.querydsl.core.types.Expression<java.time.LocalDateTime> createdAt, com.querydsl.core.types.Expression<java.time.LocalDateTime> lastModifiedAt) {
        super(CommentResponseDto.class, new Class<?>[]{long.class, long.class, String.class, String.class, String.class, java.time.LocalDateTime.class, java.time.LocalDateTime.class}, commentId, userId, nickname, fileUrl, content, createdAt, lastModifiedAt);
    }

}

