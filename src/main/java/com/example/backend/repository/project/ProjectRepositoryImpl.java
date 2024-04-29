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
        if (techStackCsv.isBlank()) return null;
        String[] split = techStackCsv.split(", ");
        BooleanExpression condition = null;
        for (String stack : split) {
            BooleanExpression stackCondition = project.techStack.contains(stack);
            condition = (condition == null) ? stackCondition : condition.and(stackCondition);
        }
        return condition;
    }

    private BooleanExpression eqPosition(String positionCsv) {
        if (positionCsv.isBlank()) return null;
        String[] split = positionCsv.split(", ");
        BooleanExpression condition = null;
        for (String position : split) {
            BooleanExpression positionCondition = project.position.contains(position);
            condition = (condition == null) ? positionCondition : condition.and(positionCondition);
        }
        return condition;
    }

    @Override
    public List<ProjectResponseDto> findProjects(Pageable pageable, String techStack, String position) {

        List<ProjectResponseDto> result = queryFactory
                .select(new QProjectResponseDto(
                        project.projectId,
                        project.user.nickname,
                        project.user.userFileUrl,
                        project.title,
                        project.techStack,
                        project.deadline,
                        project.viewCount,
                        project.favoriteCount))
                .from(project)
                .where(eqTechStack(techStack),
                        eqPosition(position))
                .orderBy()//TODO Pageable Sort 타입 바꾸기
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();




        return null;
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

}
