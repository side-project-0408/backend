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
        }

        project.updateRecruit(recruits);

        projectRepository.save(project);

        return "Project creation completed";

    }

    public ProjectResponseDto findProjects(ProjectSearchDto request) {

        //TODO
        String sort;

        // 정렬 기준 확인
        if (request.getSort() == null) sort = "createdAt";
        else sort = request.getSort();

        // 페이징
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize(),
                Sort.by(request.getSort()).descending());






        return ProjectResponseDto.builder().build();
    }

    public ProjectDetailResponseDto findProjectDetail(Long projectId) {
        //TODO
        return ProjectDetailResponseDto.builder().build();
    }

    public ProjectResponseDto findHotProjects() {
        //TODO
        return ProjectResponseDto.builder().build();
    }



}
