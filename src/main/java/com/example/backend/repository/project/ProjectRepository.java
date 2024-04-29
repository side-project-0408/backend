package com.example.backend.repository.project;

import com.example.backend.domain.Project;
import com.example.backend.repository.project.ProjectRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long>, ProjectRepositoryCustom {

    Project findByUserUserIdAndProjectId(Long userId, Long projectId);

    Project findByProjectId(Long projectId);

}
