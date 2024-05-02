package com.example.backend.repository.project;

import com.example.backend.dto.request.project.RecruitRequestDto;
import com.example.backend.dto.response.project.ProjectDetailResponseDto;
import com.example.backend.dto.response.project.ProjectResponseDto;
import com.example.backend.dto.response.project.QProjectResponseDto;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.example.backend.domain.QProject.*;
import static com.example.backend.domain.QRecruit.*;
import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.list;

@RequiredArgsConstructor
public class ProjectRepositoryImpl implements ProjectRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    private BooleanExpression eqTechStack(String techStackCsv) {
        if (techStackCsv == null) return null;
        String[] split = techStackCsv.split(", ");
        BooleanExpression condition = null;
        for (String stack : split) {
            BooleanExpression stackCondition = project.techStack.contains(stack);
            condition = (condition == null) ? stackCondition : condition.and(stackCondition);
        }
        return condition;
    }

    private BooleanExpression eqPosition(String positionCsv) {
        if (positionCsv == null) return null;
        String[] split = positionCsv.split(", ");
        BooleanExpression condition = null;
        for (String position : split) {
            BooleanExpression positionCondition = project.position.contains(position);
            condition = (condition == null) ? positionCondition : condition.and(positionCondition);
        }
        return condition;
    }

    private BooleanExpression eqSearchWord(String keword) {
        if (keword == null) return null;
        return project.title.contains(keword)
                .or(project.description.contains(keword));
    }

    @Override
    public List<ProjectResponseDto> findProjects(Pageable pageable, String techStack, String position, String keyword) {

        return queryFactory
                .select(new QProjectResponseDto(
                        project.projectId,
                        project.user.nickname,
                        project.user.userFileUrl,
                        project.title,
                        project.techStack,
                        project.position,
                        project.deadline,
                        project.viewCount,
                        project.favoriteCount,
                        project.createdAt))
                .from(project)
                .where(eqTechStack(techStack),
                        eqPosition(position),
                        eqSearchWord(keyword))
                .orderBy(project.createdAt.desc())//TODO Pageable Sort 타입 바꾸기
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

    }

    @Override
    public List<ProjectResponseDto> findHotProjects(int size) {

        return queryFactory
                .select(new QProjectResponseDto(
                        project.projectId,
                        project.user.nickname,
                        project.user.userFileUrl,
                        project.title,
                        project.techStack,
                        project.position,
                        project.deadline,
                        project.viewCount,
                        project.favoriteCount,
                        project.createdAt))
                .from(project)
                .orderBy(project.favoriteCount.desc())//TODO 인기 순위 조건 생각하기
                .limit(size)
                .fetch();

    }

    @Override
    public List<ProjectResponseDto> findFavoriteProjects(Long userId, Pageable pageable) {

        return queryFactory
                .select(new QProjectResponseDto(
                        project.projectId,
                        project.user.nickname,
                        project.user.userFileUrl,
                        project.title,
                        project.techStack,
                        project.position,
                        project.deadline,
                        project.viewCount,
                        project.favoriteCount,
                        project.createdAt))
                .from(project)
                .where(project.projectLike.contains(userId))
                .orderBy(project.createdAt.desc())//TODO Pageable Sort 타입 바꾸기
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

    }

    @Override
    public List<ProjectDetailResponseDto> findDetailByProjectId(Long projectId) {

        return queryFactory
                .selectFrom(project)
                .leftJoin(recruit).on(project.projectId.eq(recruit.project.projectId))
                .where(project.projectId.eq(projectId))
                .transform(groupBy(project.projectId).list(Projections.constructor(ProjectDetailResponseDto.class,
                        project.projectId,
                        project.user.userId,
                        project.user.nickname,
                        project.user.userFileUrl,
                        project.projectFileUrl,
                        project.title,
                        project.techStack,
                        project.softSkill,
                        project.importantQuestion,
                        project.deadline,
                        project.recruitment,
                        project.user.employmentStatus,
                        project.viewCount,
                        project.favoriteCount,
                        project.description,
                        project.createdAt,
                        project.lastModifiedAt,
                        list(Projections.constructor(RecruitRequestDto.class,
                                recruit.position,
                                recruit.currentCount,
                                recruit.targetCount)))));

    }

    @Override
    public List<ProjectResponseDto> findMyProjects(Long userId, Pageable pageable) {

        return queryFactory
                .select(new QProjectResponseDto(
                        project.projectId,
                        project.user.nickname,
                        project.user.userFileUrl,
                        project.title,
                        project.techStack,
                        project.position,
                        project.deadline,
                        project.viewCount,
                        project.favoriteCount,
                        project.createdAt))
                .from(project)
                .where(project.user.userId.eq(userId))
                .orderBy(project.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

    }

}
