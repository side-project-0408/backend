package com.example.backend.controller;

import com.example.backend.common.response.CommonApiResponse;
import com.example.backend.dto.request.project.ProjectRequestDto;
import com.example.backend.dto.request.project.ProjectSearchDto;
import com.example.backend.service.ProjectService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    // 프로젝트 저장
    @PostMapping("/projects")
    public CommonApiResponse<?> postProject(@RequestPart ProjectRequestDto request,
                                            @RequestPart(required = false) MultipartFile file,
                                            HttpServletRequest servletRequest) throws IOException {
        return new CommonApiResponse<>("success", projectService.postProject(request, file, servletRequest));
    }

    // 프로젝트 목록 가져오기
    @GetMapping("/projects")
    public CommonApiResponse<?> getProjects(@ModelAttribute ProjectSearchDto request) {
        return new CommonApiResponse<>("success", projectService.findProjects(request));
    }

    // 프로젝트 상세 정보 가져오기
    @GetMapping("/projects/{projectId}")
    public CommonApiResponse<?> getProject(@PathVariable Long projectId) {
        return new CommonApiResponse<>("success", projectService.findProject(projectId));
    }

    // 핫 프로젝트 목록 가져오기
    @GetMapping("/projects/hot")
    public CommonApiResponse<?> getHotProjects(@RequestParam int size) {
        return new CommonApiResponse<>("success", projectService.findHotProjects(size));
    }

}
