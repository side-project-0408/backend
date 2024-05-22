package com.example.backend.repository.comment;

import com.example.backend.dto.response.comment.CommentResponseDto;
import com.example.backend.dto.response.comment.QCommentResponseDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

import java.util.List;

import static com.example.backend.domain.QComment.comment;

@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    // 댓글 목록 가져오기
    @Override
    public Slice<CommentResponseDto> findSliceByProjectId(Long projectId, Pageable pageable) {

        List<CommentResponseDto> content = queryFactory.select(new QCommentResponseDto(
                        comment.commentId,
                        comment.user.userId,
                        comment.user.nickname,
                        comment.user.userFileUrl,
                        comment.content,
                        comment.createdAt,
                        comment.lastModifiedAt))
                .from(comment)
                .where(comment.project.projectId.eq(projectId))
                .orderBy(comment.createdAt.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                .fetch();

        boolean hasNext = false;
        if (content.size() > pageable.getPageSize()) {
            content.remove(pageable.getPageSize());
            hasNext = true;
        }

        return new SliceImpl<>(content, pageable, hasNext);

    }

}
