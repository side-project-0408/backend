package com.example.backend.repository.project;

import com.example.backend.domain.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long>, ProjectRepositoryCustom {

    Project findByProjectId(Long projectId);

}
