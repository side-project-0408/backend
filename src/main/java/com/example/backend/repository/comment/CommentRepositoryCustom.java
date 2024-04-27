package com.example.backend.repository.comment;

import com.example.backend.dto.response.comment.CommentResponseDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface CommentRepositoryCustom {

    Slice<CommentResponseDto> findSliceByProjectId(Long projectId, Pageable pageable);

}
