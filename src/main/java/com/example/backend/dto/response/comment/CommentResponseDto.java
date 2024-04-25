package com.example.backend.dto.response.comment;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class CommentResponseDto {

    private Long commentId;

    private Long userId;

    private String nickname;

    private String fileUrl;

    private String content;

    private LocalDateTime createdAt;

    private LocalDateTime lastModifiedAt;

    @QueryProjection
    public CommentResponseDto(Long commentId, Long userId, String nickname, String fileUrl, String content, LocalDateTime createdAt, LocalDateTime lastModifiedAt) {
        this.commentId = commentId;
        this.userId = userId;
        this.nickname = nickname;
        this.fileUrl = fileUrl;
        this.content = content;
        this.createdAt = createdAt;
        this.lastModifiedAt = lastModifiedAt;
    }
}
