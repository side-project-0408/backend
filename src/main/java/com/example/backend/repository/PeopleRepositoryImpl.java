package com.example.backend.repository;

import com.example.backend.dto.request.people.HotSearchDto;
import com.example.backend.dto.request.people.PeopleSearchDto;
import com.example.backend.dto.response.people.PeopleResponseDto;
import com.example.backend.dto.response.people.QPeopleResponseDto;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;

import java.util.List;

import static com.example.backend.domain.QUser.user;
import static org.springframework.util.StringUtils.hasText;

public class PeopleRepositoryImpl implements PeopleRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    private final EntityManager em;

    public PeopleRepositoryImpl(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<PeopleResponseDto> findPeoples(PeopleSearchDto dto) {

        List<PeopleResponseDto> result = queryFactory
                .select(new QPeopleResponseDto(
                        user.nickname,
                        user.favoriteCount,
                        user.viewCount,
                        user.position,
                        user.userFileUrl))
                .from(user)
                .where(nicknameEq(dto.getNickname()),
                        techSizeEq(dto.getTechSize()),
                        positionEq(dto.getPosition()))
                .orderBy(user.createdAt.desc())
                .offset(dto.getPage())
                .limit(10)
                .fetch();
        return result;
    }

    @Override
    public List<PeopleResponseDto> findHotPeoples(HotSearchDto dto) {
        List<PeopleResponseDto> result = queryFactory
                .select(new QPeopleResponseDto(
                        user.nickname,
                        user.favoriteCount,
                        user.viewCount,
                        user.position,
                        user.userFileUrl))
                .from(user)
                .orderBy(user.viewCount.desc())
                .offset(dto.getPage())
                .limit(10)
                .fetch();
        return result;
    }

    private BooleanExpression nicknameEq(String nickname) {
        return hasText(nickname) ? user.nickname.eq(nickname) : null;
    }

    /**
     * 수정 필요
     * 기술 스택 (java로 검색시 javascript도 동시에 출력되는 현상 해결해야 함)
     */
    private BooleanExpression techSizeEq(String techSize) {
        if (!hasText(techSize)) {
            return null;
        }

        String[] split = techSize.split(",");
        BooleanExpression condition = null;

        for (String stack : split) {
            BooleanExpression stackCondition = user.techStack.contains(stack);

            if (condition == null) {
                condition = stackCondition;
            } else {
                condition = condition.or(stackCondition);
            }
        }

        return condition;
    }

    private BooleanExpression positionEq(String position) {
        return hasText(position) ? user.position.eq(position) : null;
    }
}
