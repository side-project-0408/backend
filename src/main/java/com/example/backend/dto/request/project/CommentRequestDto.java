package com.example.backend.dto.request.project;

import lombok.Getter;

@Getter
public class CommentRequestDto {

    private Long commentId;

    private Long userId;

    private String content;

    private int size;

}
