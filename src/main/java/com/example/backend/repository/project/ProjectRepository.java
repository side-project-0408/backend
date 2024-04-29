package com.example.backend.repository.project;

import com.example.backend.domain.Project;
import com.example.backend.repository.project.ProjectRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project, Long>, ProjectRepositoryCustom {

    Optional<Project> findByUserIdAndProjectId(Long userId, Long projectId);

    Project findByProjectId(Long projectId);

}
