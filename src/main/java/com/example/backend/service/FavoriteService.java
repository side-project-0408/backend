package com.example.backend.service;

import com.example.backend.domain.Project;
import com.example.backend.domain.User;
import com.example.backend.dto.request.FavoriteRequest;
import com.example.backend.repository.PeopleRepository;
import com.example.backend.repository.project.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class FavoriteService {

    private final ProjectRepository projectRepository;
    private final PeopleRepository peopleRepository;

    public String projectFavorite(FavoriteRequest request) {

        Project project = projectRepository.findByProjectId(request.getProjectId());

        project.addProjectLike(request.getUserId());
        project.updateFavoriteCount(1);

        return "Project favorite success";

    }

    public String projectFavoriteCancel(Long projectId, Long userId) {

        Project project = projectRepository.findByProjectId(projectId);

        project.updateFavoriteCount(-1);
        project.getProjectLike().remove(userId);

        return "Project favorite cancel success";

    }
/*
    public String userFavorite(FavoriteRequest request) {

        User user = peopleRepository.findUserById(request.getFavoriteId());

        user.addProjectLike(request.getUserId());
        user.updateFavoriteCount(1);

        return "User favorite success";

    }

    public String userFavoriteCancel(Long favoriteId, Long userId) {

        User user = peopleRepository.findUserById(favoriteId);

        user.updateFavoriteCount(-1);
        user.getUserLike().remove(userId);

        return "User favorite cancel success";

    }

 */

}
