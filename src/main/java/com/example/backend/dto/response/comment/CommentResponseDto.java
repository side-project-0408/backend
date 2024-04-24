package com.example.backend.dto.response.comment;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
public class CommentResponseDto {

    private Long commentId;

    private Long userId;

    private String nickname;

    private String fileUrl;

    private String content;

    private LocalDateTime createdAt;

    private LocalDateTime lastModifiedAt;

}
