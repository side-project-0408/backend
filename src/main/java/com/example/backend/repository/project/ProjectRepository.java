package com.example.backend.repository.project;

import com.example.backend.domain.Project;
import com.example.backend.dto.request.project.ProjectSearchDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

}
