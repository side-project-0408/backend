package com.example.backend.repository.comment;

import com.example.backend.domain.Comment;
import com.example.backend.dto.response.comment.CommentResponseDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;

public interface CommentRepositoryCustom {

    Slice<CommentResponseDto> findSliceByProject(Long projectId, Pageable pageable);

}
