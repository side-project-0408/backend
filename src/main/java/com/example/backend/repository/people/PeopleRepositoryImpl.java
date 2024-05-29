package com.example.backend.repository.people;

import com.example.backend.dto.request.people.HotSearchDto;
import com.example.backend.dto.request.people.PeopleSearchDto;
import com.example.backend.dto.response.people.PeopleResponseDto;
import com.example.backend.dto.response.people.QPeopleResponseDto;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.example.backend.domain.QProject.project;
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

        OrderSpecifier<?> orderCondition = dto.getSort().equalsIgnoreCase("POPULAR")
                ? user.favoriteCount.add(project.viewCount).desc()
                : user.createdAt.desc();

        List<PeopleResponseDto> result = queryFactory
                .select(new QPeopleResponseDto(
                        user.nickname,
                        user.favoriteCount,
                        user.viewCount,
                        user.position,
                        user.userFileUrl,
                        user.techStack,
                        user.softSkill,
                        user.userId))
                .from(user)
                .where(techSizeEq(dto.getTechSize()),
                        positionEq(dto.getPosition()),
                        keywordEq(dto.getKeyword()))
                .orderBy(orderCondition)
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
                        user.userFileUrl,
                        user.techStack,
                        user.softSkill,
                        user.userId))
                .from(user)
                .orderBy(user.viewCount.add(user.favoriteCount).desc())
                .offset(dto.getPage())
                .limit(10)
                .fetch();
        return result;
    }

    //내가 찜한 사람 목록
    @Override
    public List<PeopleResponseDto> findFavoritePeoples(Long peopleId, Pageable pageable) { //TODO page 통일할부분 생각하기
        List<PeopleResponseDto> result = queryFactory
                .select(new QPeopleResponseDto(
                        user.nickname,
                        user.favoriteCount,
                        user.viewCount,
                        user.position,
                        user.userFileUrl,
                        user.techStack,
                        user.softSkill,
                        user.userId))
                .from(user)
                .where(user.userLike.contains(peopleId))
                .orderBy(user.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        return result;
    }

    //기술 스택 조건 검색
    private BooleanExpression techSizeEq(String techSize) {
        if (techSize == null || techSize.trim().isEmpty()) return null;

        String[] split = techSize.split(", ");
        BooleanExpression condition = null;

        for (String stack : split) {
            BooleanExpression stackCondition = user.techStack.contains(stack);
            condition = (condition == null) ? stackCondition : condition.and(stackCondition);
        }
        return condition;
    }

    private BooleanExpression positionEq(String position) {
        return hasText(position) ? user.position.eq(position) : null;
    }

    private BooleanExpression keywordEq(String keyword) {
        if (keyword == null) return null;
        return user.nickname.contains(keyword)
                .or(user.content.contains(keyword));
    }
}
