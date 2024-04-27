package com.example.backend.repository.project;

import com.example.backend.dto.request.project.RecruitRequestDto;
import com.example.backend.dto.response.project.ProjectDetailResponseDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.example.backend.domain.QProject.*;
import static com.example.backend.domain.QRecruit.*;
import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.list;

@RequiredArgsConstructor
public class ProjectRepositoryImpl implements ProjectRepositoryCustom {

    private final JPAQueryFactory queryFactory;

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
