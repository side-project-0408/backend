package com.example.backend.dto.response.project;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.example.backend.dto.response.project.QProjectResponseDto is a Querydsl Projection type for ProjectResponseDto
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QProjectResponseDto extends ConstructorExpression<ProjectResponseDto> {

    private static final long serialVersionUID = -1691062259L;

    public QProjectResponseDto(com.querydsl.core.types.Expression<Long> projectId, com.querydsl.core.types.Expression<String> nickname, com.querydsl.core.types.Expression<String> userFileUrl, com.querydsl.core.types.Expression<String> title, com.querydsl.core.types.Expression<String> techStack, com.querydsl.core.types.Expression<java.time.LocalDate> deadline, com.querydsl.core.types.Expression<Integer> viewCount, com.querydsl.core.types.Expression<Integer> favoriteCount) {
        super(ProjectResponseDto.class, new Class<?>[]{long.class, String.class, String.class, String.class, String.class, java.time.LocalDate.class, int.class, int.class}, projectId, nickname, userFileUrl, title, techStack, deadline, viewCount, favoriteCount);
    }

}

