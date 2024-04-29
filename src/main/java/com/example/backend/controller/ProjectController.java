package com.example.backend.controller;

import com.example.backend.common.response.CommonApiResponse;
import com.example.backend.dto.request.project.ProjectRequestDto;
import com.example.backend.dto.request.project.ProjectSearchDto;
import com.example.backend.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping("/projects")
    public CommonApiResponse<?> postProject(@RequestBody ProjectRequestDto request) {
        return new CommonApiResponse<>("success", projectService.postProject(request));
    }

    @GetMapping("/projects")
    public CommonApiResponse<?> getProjects(@ModelAttribute ProjectSearchDto request) {
        System.out.println(request.toString());
        return new CommonApiResponse<>("success", projectService.findProjects(request));
    }

    @GetMapping("/projects/{projectId}")
    public CommonApiResponse<?> getProject(@PathVariable Long projectId) {
        return new CommonApiResponse<>("success", projectService.findProject(projectId));
    }

    @GetMapping("/projects/hot")
    public CommonApiResponse<?> getHotProjects(@RequestParam int size) {
        return new CommonApiResponse<>("success", projectService.findHotProjects(size));
    }

}
