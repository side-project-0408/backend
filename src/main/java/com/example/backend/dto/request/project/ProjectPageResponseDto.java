package com.example.backend.dto.request.project;

import com.example.backend.dto.response.project.ProjectResponseDto;
import lombok.Getter;

import java.util.List;

@Getter
public class ProjectPageResponseDto {
    private List<ProjectResponseDto> projects;
    private int totalPages;
    private long totalElements;

    public ProjectPageResponseDto(List<ProjectResponseDto> projects, int totalPages, long totalElements) {
        this.projects = projects;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
    }

}
