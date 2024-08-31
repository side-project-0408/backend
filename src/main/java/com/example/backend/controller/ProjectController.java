package com.example.backend.controller;

import com.example.backend.common.response.CommonApiResponse;
import com.example.backend.common.response.PageApiResponse;
import com.example.backend.dto.request.project.ProjectRequestDto;
import com.example.backend.dto.request.project.ProjectSearchDto;
import com.example.backend.service.AwsS3Service;
import com.example.backend.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    private final AwsS3Service awsS3Service;

    // 프로젝트 저장
    @PostMapping("/projects")
    public CommonApiResponse<?> create(@RequestPart ProjectRequestDto dto,
                                            @RequestPart(required = false) MultipartFile file,
                                            Authentication authentication) throws IOException {
        return new CommonApiResponse<>(OK, projectService.create(dto, file, authentication));
    }

    // 프로젝트 목록 가져오기
    @GetMapping("/projects")
    public PageApiResponse<?> findList(@ModelAttribute ProjectSearchDto request) {
        return projectService.findList(request);
    }

    // 프로젝트 상세 정보 가져오기
    @GetMapping("/projects/{projectId}")
    public CommonApiResponse<?> findById(@PathVariable Long projectId) {
        return new CommonApiResponse<>(OK, projectService.findById(projectId));
    }

    // 핫 프로젝트 목록 가져오기
    @GetMapping("/projects/hot")
    public CommonApiResponse<?> findHotList(@RequestParam int size) {
        return new CommonApiResponse<>(OK, projectService.findHotList(size));
    }

}