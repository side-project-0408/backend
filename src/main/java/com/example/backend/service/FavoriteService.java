package com.example.backend.service;

import com.example.backend.domain.Project;
import com.example.backend.dto.request.FavoriteRequest;
import com.example.backend.repository.project.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FavoriteService {

    private final ProjectRepository projectRepository;

    public String projectFavorite(FavoriteRequest request) {

        Project project = projectRepository.findByProjectId(request.getProjectId());

        project.addProjectLike(request.getUserId());
        project.updateFavoriteCount();

        return "Project favorite success";
    }

}
