package com.example.backend.repository.comment;

import com.example.backend.domain.Comment;
import com.example.backend.dto.response.comment.CommentResponseDto;

import java.util.List;

public interface CommentRepositoryCustom {

    List<CommentResponseDto> findSliceByProject(Long projectId, int page, int size);

}
