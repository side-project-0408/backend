package com.example.backend.controller;

import com.example.backend.common.response.CommonApiResponse;
import com.example.backend.domain.Project;
import com.example.backend.domain.User;
import com.example.backend.dto.request.people.UpdateUserRequestDto;
import com.example.backend.dto.request.project.ProjectRequestDto;
import com.example.backend.dto.request.project.ProjectSearchDto;
import com.example.backend.dto.response.people.PeopleDetailResponseDto;
import com.example.backend.repository.people.PeopleRepository;
import com.example.backend.repository.project.ProjectRepository;
import com.example.backend.service.JwtService;
import com.example.backend.service.PeopleService;
import com.example.backend.service.ProjectService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class MyPageController {

    private final PeopleRepository peopleRepository;
    private final PeopleService peopleService;
    private final ProjectRepository projectRepository;
    private final ProjectService projectService;

    private final JwtService jwtService;

    //마이페이지 내 정보 조회
    @GetMapping("/users/{userId}")
    public CommonApiResponse<PeopleDetailResponseDto> getUser(@PathVariable("userId") Long userId) {
        User user = peopleRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("해당 사용자를 찾을 수 없습니다."));

        PeopleDetailResponseDto dto = new PeopleDetailResponseDto(user);
        return new CommonApiResponse<>("success", dto);
    }

    //마이페이지 내 정보 수정
    @PatchMapping("/users/{userId}")
    public CommonApiResponse<?> editUser(@PathVariable("userId") Long userId, @RequestBody UpdateUserRequestDto dto) throws IOException {

        return new CommonApiResponse<>("success", peopleService.update(userId, dto));
    }

    //내가 작성한 프로젝트 수정
    @PatchMapping("/posts/{projectId}")
    public CommonApiResponse<?> updateProject(@PathVariable("projectId") Long projectId, @RequestBody ProjectRequestDto request, HttpServletRequest servletRequest) {
        return new CommonApiResponse<>("success", projectService.updateProject(projectId, request, servletRequest));
    }


    //내가 작성한 프로젝트 삭제
    @DeleteMapping("/posts/{projectId}")
    public CommonApiResponse<?> deletePost(@PathVariable("projectId") Long projectId, HttpServletRequest servletRequest) {

        Project project = projectRepository.findByUserUserIdAndProjectId(jwtService.getUserIdFromToken(servletRequest), projectId);
        if(project == null) {
            throw new RuntimeException("해당 프로젝트는 존재하지 않습니다.");
        }
        projectRepository.delete(project);

        return new CommonApiResponse<>("success", "프로젝트가 삭제되었습니다.");
    }

    //내가 찜한 프로젝트 목록
    @GetMapping("/projects/favorite")
    public CommonApiResponse<?> getFavoriteProjects(@ModelAttribute ProjectSearchDto request, HttpServletRequest servletRequest) {
        return new CommonApiResponse<>("success", projectService.findFavoriteProjects(servletRequest, request));
    }

    //내가 작성한 프로젝트 목록
    @GetMapping("/posts")
    public CommonApiResponse<?> getMyProjects(@ModelAttribute ProjectSearchDto request, HttpServletRequest servletRequest) {
        return new CommonApiResponse<>("success", projectService.findMyProjects(servletRequest, request));
    }

}
