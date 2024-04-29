package com.example.backend.repository.project;

import com.example.backend.domain.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long>, ProjectRepositoryCustom {
    Project findByUserUserIdAndProjectId(Long userId, Long projectId);
    Project findByProjectId(Long projectId);

}
