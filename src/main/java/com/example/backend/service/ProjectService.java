package com.example.backend.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.example.backend.domain.Project;
import com.example.backend.domain.Recruit;
import com.example.backend.domain.User;
import com.example.backend.dto.request.project.ProjectRequestDto;
import com.example.backend.dto.request.project.ProjectSearchDto;
import com.example.backend.dto.request.project.RecruitRequestDto;
import com.example.backend.dto.response.project.ProjectDetailResponseDto;
import com.example.backend.dto.response.project.ProjectResponseDto;
import com.example.backend.repository.project.ProjectRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final AmazonS3Client amazonS3Client;
    private final AwsS3Service awsS3Service;
    private final JwtService jwtService;

    // 프로젝트 저장
    public String postProject(ProjectRequestDto request, MultipartFile file, HttpServletRequest servletRequest) throws IOException {

        List<Recruit> recruits = new ArrayList<>();

        String position = "";
        String fileUrl = "";
        if(!file.isEmpty() || file == null) {
            fileUrl = awsS3Service.upload(file);
        }

        Project project = Project.builder()
                .user(User.builder().userId(jwtService.getUserIdFromToken(servletRequest)).build())
                .title(request.getTitle())
                .projectFileUrl(fileUrl)
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

        return "프로젝트 저장 완료";

    }

    // 프로젝트 목록 가져오기
    public List<ProjectResponseDto> findProjects(ProjectSearchDto request) {

        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());

        ProjectSearchDto searchDto = ProjectSearchDto.builder()
                .techStack(request.getTechStack())
                .position(request.getPosition())
                .keyword(request.getKeyword())
                .sort(request.getSort())
                .build();

        return checkRecent(projectRepository.findProjects(pageable, searchDto));

    }

    // 프로젝트 상세 정보 가져오기
    public ProjectDetailResponseDto findProject(Long projectId) {

        List<ProjectDetailResponseDto> content = projectRepository.findDetailByProjectId(projectId);

        if (content.isEmpty()) return null;

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

    // 핫 프로젝트 목록 가져오기
    public List<ProjectResponseDto> findHotProjects(int size) {
        return checkRecent(projectRepository.findHotProjects(size));
    }

    public List<ProjectResponseDto> findFavoriteProjects(HttpServletRequest servletRequest, ProjectSearchDto request) {
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        return checkRecent(projectRepository.findFavoriteProjects(jwtService.getUserIdFromToken(servletRequest), pageable));
    }

    // 내가 작성한 프로젝트 가져오기
    public List<ProjectResponseDto> findMyProjects(HttpServletRequest servletRequest, ProjectSearchDto request) {
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        return checkRecent(projectRepository.findMyProjects(jwtService.getUserIdFromToken(servletRequest), pageable));
    }

    // 프로젝트 수정
    public String updateProject(Long projectId, ProjectRequestDto request, MultipartFile file,
                                HttpServletRequest servletRequest) throws IOException {

        Project project = projectRepository.findByProjectId(projectId);

        if(!(project.getUser().getUserId() == jwtService.getUserIdFromToken(servletRequest)))
            throw new RuntimeException("프로젝트 작성자가 아닙니다.");

        List<Recruit> recruits = project.getRecruits();

        String position = "";
        String fileUrl = "";

        if(!file.isEmpty() || file == null) {
            awsS3Service.deleteFileFromS3(project.getProjectFileUrl()); //기존 파일 삭제
            fileUrl = awsS3Service.upload(file);
        }


        if (!recruits.isEmpty()) recruits.clear();

        for (RecruitRequestDto recruitDto : request.getRecruit()) {
            recruits.add(Recruit.builder()
                    .project(project)
                    .position(recruitDto.getPosition())
                    .currentCount(recruitDto.getCurrentCount())
                    .targetCount(recruitDto.getTargetCount())
                    .build());
            position += recruitDto.getPosition() + ", ";
        }

        project.updateTitle(request.getTitle());
        project.updateProjectFileUrl(fileUrl);
        project.updateDeadline(request.getDeadline());
        project.updateImportantQuestion(request.getImportantQuestion());
        project.updateSoftSkill(request.getSoftSkill());
        project.updateTechStack(request.getTechStack());
        project.updateDescription(request.getDescription());
        project.updateRecruit(recruits);
        project.updatePosition(position.substring(0, position.length() - 2));
        project.updateLastModifiedAt(LocalDateTime.now());

        return "프로젝트 수정 완료";

    }

    // 신규 스티커 여부 (생성한 후 1주일)
    public List<ProjectResponseDto> checkRecent(List<ProjectResponseDto> projects){
        for (ProjectResponseDto project : projects) {
            boolean recent = !project.getCreatedAt().isBefore(LocalDateTime.now().minusDays(1));
            project.setRecent(recent);
        }
        return projects;
    }

}
