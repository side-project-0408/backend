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

    public QPeopleResponseDto(com.querydsl.core.types.Expression<String> nickname, com.querydsl.core.types.Expression<Integer> favoriteCount, com.querydsl.core.types.Expression<Integer> viewCount, com.querydsl.core.types.Expression<String> position, com.querydsl.core.types.Expression<String> userFileUrl) {
        super(PeopleResponseDto.class, new Class<?>[]{String.class, int.class, int.class, String.class, String.class}, nickname, favoriteCount, viewCount, position, userFileUrl);
    }

}

