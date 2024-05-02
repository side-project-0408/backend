package com.example.backend.service;

import com.example.backend.domain.Project;
import com.example.backend.domain.User;
import com.example.backend.dto.request.FavoriteRequest;
import com.example.backend.repository.people.PeopleRepository;
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

    // 프로젝트 찜하기
    public String projectFavorite(FavoriteRequest request) {

        Project project = projectRepository.findByProjectId(request.getProjectId());

        project.addProjectLike(request.getUserId());
        project.updateFavoriteCount(1);

        return "프로젝트 찜하기 완료";

    }

    // 프로젝트 찜하기 취소
    public String projectFavoriteCancel(Long projectId, Long userId) {

        Project project = projectRepository.findByProjectId(projectId);

        project.updateFavoriteCount(-1);
        project.getProjectLike().remove(userId);

        return "프로젝트 찜하기 취소 완료";

    }

    public String userFavorite(FavoriteRequest request) {

        User user = peopleRepository.findUserByUserId(request.getFavoriteId());

        user.addProjectLike(request.getUserId());
        user.updateFavoriteCount(1);

        return "유저 찜하기 완료";

    }

    public String userFavoriteCancel(Long favoriteId, Long userId) {

        User user = peopleRepository.findUserByUserId(favoriteId);

        user.updateFavoriteCount(-1);
        user.getUserLike().remove(userId);

        return "유저 찜하기 취소 완료";

    }

}
