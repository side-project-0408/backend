package com.example.backend.repository.comment;

import com.example.backend.dto.response.comment.CommentResponseDto;
import com.example.backend.dto.response.comment.QCommentResponseDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.example.backend.domain.QComment.*;

@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<CommentResponseDto> findSliceByProject(Long projectId, int page, int size) {

        List<CommentResponseDto> result = queryFactory.select(new QCommentResponseDto(
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
                    .offset(page)
                    .limit(size + 1)
                    .fetch();

        System.out.println(result.toString());

        return result;

    }

}
