package com.example.backend.repository.people;

import com.example.backend.dto.request.people.HotSearchDto;
import com.example.backend.dto.request.people.PeopleSearchDto;
import com.example.backend.dto.response.people.PeopleResponseDto;
import com.example.backend.dto.response.people.QPeopleResponseDto;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

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
                        user.userFileUrl,
                        user.techStack,
                        user.softSkill))
                .from(user)
                .where(techSizeEq(dto.getTechSize()),
                        positionEq(dto.getPosition()),
                        keywordEq(dto.getKeyword()))
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
                        user.userFileUrl,
                        user.techStack,
                        user.softSkill))
                .from(user)
                .orderBy(user.viewCount.desc()) //TODO 최신순, 인기순 목록 어떻게 구현할지
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
                        user.softSkill))
                .from(user)
                .where(user.userLike.contains(peopleId))
                .orderBy(user.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        return result;
    }

    /**
     * 수정 필요
     * 기술 스택 (java로 검색시 javascript도 동시에 출력되는 현상 해결해야 함)
     */

    private BooleanExpression techSizeEq(String techSize) {
        if (techSize == null || techSize.trim().isEmpty()) {
            return null;  // 조건이 없을 때 null 반환
        }

        String[] split = techSize.split(",\\s*");  // techSize 문자열 분리
        BooleanExpression condition = null;

        for (String stack : split) {
            // user.techStack에서 정확하게 일치하는지 확인
            BooleanExpression stackCondition = user.techStack.like("%," + stack + ",%")  // 중간에 있는 경우
                    .or(user.techStack.startsWith(stack + ","))  // 처음에 있는 경우
                    .or(user.techStack.endsWith("," + stack))  // 끝에 있는 경우
                    .or(user.techStack.eq(stack));  // 전체가 동일한 경우

            condition = (condition == null) ? stackCondition : condition.and(stackCondition);  // 조건 연결
        }

        return condition;  // 조건 반환
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
