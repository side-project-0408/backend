package com.example.backend.service;

import com.example.backend.domain.Project;
import com.example.backend.domain.Recruit;
import com.example.backend.domain.User;
import com.example.backend.dto.request.project.ProjectRequestDto;
import com.example.backend.dto.request.project.ProjectSearchDto;
import com.example.backend.dto.request.project.RecruitRequestDto;
import com.example.backend.dto.response.project.ProjectDetailResponseDto;
import com.example.backend.dto.response.project.ProjectResponseDto;
import com.example.backend.repository.project.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;

    public String postProject(ProjectRequestDto request) {

        List<Recruit> recruits = new ArrayList<>();

        String position = "";

        Project project = Project.builder().user(User.builder().userId(request.getCreatedId()).build())
                .title(request.getTitle())
                .projectFileUrl(request.getProjectFileUrl())
                .deadline(request.getDeadline())
                .softSkill(request.getSoftSkill())
                .importantQuestion(request.getImportantQuestion())
                .techStack(request.getTechStack())
                .description(request.getDescription())
                .recruits(recruits)
                .recruitment("OPEN")
                .createdAt(LocalDateTime.now())
                .build();

        for (RecruitRequestDto recruitDto : request.getRecruit()) {
            recruits.add(Recruit.builder()
                    .project(project)
                    .position(recruitDto.getPosition())
                    .currentCount(recruitDto.getCurrentCount())
                    .targetCount(recruitDto.getTargetCount())
                    .build());
            position += recruitDto.getPosition() + ", ";
        }

        project.updatePosition(position.substring(0, position.length() - 2));
        project.updateRecruit(recruits);

        projectRepository.save(project);

        return "Project creation completed";

    }

    public List<ProjectResponseDto> findProjects(ProjectSearchDto request) {

        String sort = request.getSort() == null ? "createdAt" : request.getSort();

        Pageable pageable = PageRequest.of(request.getPage(), request.getSize(), Sort.by(sort).descending());

        return checkRecent(projectRepository.findProjects(pageable, request.getTechStack(), request.getPosition()));

    }

    public List<ProjectResponseDto> findHotProjects(int size) {
        return checkRecent(projectRepository.findHotProjects(size));
    }

    public ProjectDetailResponseDto findProject(Long projectId) {

        List<ProjectDetailResponseDto> content = projectRepository.findDetailByProjectId(projectId);

        return ProjectDetailResponseDto.builder()
                .projectId(content.get(0).getProjectId())
                .userId(content.get(0).getUserId())
                .nickname(content.get(0).getNickname())
                .userFileUrl(content.get(0).getUserFileUrl())
                .projectFileUrl(content.get(0).getProjectFileUrl())
                .title(content.get(0).getTitle())
                .techStack(content.get(0).getTechStack())
                .softSkill(content.get(0).getSoftSkill())
                .importantQuestion(content.get(0).getImportantQuestion())
                .deadline(content.get(0).getDeadline())
                .recruitment(content.get(0).getRecruitment())
                .employmentStatus(content.get(0).getEmploymentStatus())
                .viewCount(content.get(0).getViewCount())
                .favoriteCount(content.get(0).getFavoriteCount())
                .description(content.get(0).getDescription())
                .createdAt(content.get(0).getCreatedAt())
                .lastModifiedAt(content.get(0).getLastModifiedAt())
                .recruit(content.get(0).getRecruit())
                .build();

    }

    public List<ProjectResponseDto> findFavoriteProjects(Long userId, ProjectSearchDto request) {

        String sort = request.getSort() == null ? "createdAt" : request.getSort();

        Pageable pageable = PageRequest.of(request.getPage(), request.getSize(), Sort.by(sort).descending());

        return checkRecent(projectRepository.findFavoriteProjects(userId, pageable));

    }

    // 신규 스티커 여부 (생성한 후 1주일)
    public List<ProjectResponseDto> checkRecent(List<ProjectResponseDto> projects){
        for (ProjectResponseDto project : projects) {
            boolean recent = project.getCreatedAt().isBefore(LocalDateTime.now().plusWeeks(1)) ? true : false;
            project.setRecent(recent);
        }
        return projects;
    }

}
