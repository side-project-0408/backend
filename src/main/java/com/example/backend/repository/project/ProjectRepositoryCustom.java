package com.example.backend.repository.project;

import com.example.backend.dto.response.project.ProjectDetailResponseDto;

import java.util.List;

public interface ProjectRepositoryCustom {

    List<ProjectDetailResponseDto> findDetailByProjectId(Long projectId);

}
