package com.example.backend.repository.project;

import com.example.backend.domain.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long>, ProjectRepositoryCustom {
    Project findByUserUserIdAndProjectId(Long userId, Long projectId);

    Project findByProjectId(Long projectId);
    @Query("SELECT p.projectId FROM Project p WHERE p.user.userId = :userId")
    List<Long> getProjectIdsByUserId(@Param("userId") Long userId);
}
