package com.example.backend.dto.response.people;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.example.backend.dto.response.people.QPeopleResponseDto is a Querydsl Projection type for PeopleResponseDto
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QPeopleResponseDto extends ConstructorExpression<PeopleResponseDto> {

    private static final long serialVersionUID = -1617076029L;

    public QPeopleResponseDto(com.querydsl.core.types.Expression<String> nickname, com.querydsl.core.types.Expression<Integer> favoriteCount, com.querydsl.core.types.Expression<Integer> viewCount, com.querydsl.core.types.Expression<String> position, com.querydsl.core.types.Expression<String> userFileUrl, com.querydsl.core.types.Expression<String> techStack, com.querydsl.core.types.Expression<String> softSkill, com.querydsl.core.types.Expression<Long> userId, com.querydsl.core.types.Expression<java.time.LocalDateTime> createdAt) {
        super(PeopleResponseDto.class, new Class<?>[]{String.class, int.class, int.class, String.class, String.class, String.class, String.class, long.class, java.time.LocalDateTime.class}, nickname, favoriteCount, viewCount, position, userFileUrl, techStack, softSkill, userId, createdAt);
    }

}

