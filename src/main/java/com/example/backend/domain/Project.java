package com.example.backend.domain;

import com.example.backend.dto.request.project.UpdateProjectRequestDto;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.FetchType.*;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Project {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_id")
    private Long projectId;

    private String title;

    @Column(name = "file_url")
    private String projectFileUrl;

    private LocalDate deadline;

    private String recruitment;

    private String position;

    @Column(name = "tech_stack")
    private String techStack;

    @Column(name = "soft_skill")
    private String softSkill;

    @Column(name = "important_question")
    private String importantQuestion;

    @Column(name = "view_count")
    private int viewCount;

    @Column(name = "favorite_count")
    private int favoriteCount;

    @Lob
    private String description;

    @Column(name = "last_modified_at")
    private LocalDateTime lastModifiedAt;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Builder.Default
    @ElementCollection
    @CollectionTable(name = "projectLike", joinColumns = @JoinColumn(name = "project_id"))
    @Column(name = "user_id")
    private Set<Long> projectLike = new HashSet<>();

    @ManyToOne(fetch = LAZY, cascade = PERSIST)
    @JoinColumn(name = "created_id")
    private User user;

    @Builder.Default
    @OneToMany(mappedBy = "project", cascade = ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "project", cascade = ALL, orphanRemoval = true)
    private List<Recruit> recruits = new ArrayList<>();

    public void updateRecruit(List<Recruit> recruits) {
        this.recruits = recruits;
    }

    public void updateFavoriteCount(int count) {this.favoriteCount += count;}

    public void addProjectLike(Long userId) {this.projectLike.add(userId);}

    public void updatePosition(String position) {this.position = position;}

    public void updateTitle(String title) {this.title = title;}

    public void updateProjectFileUrl(String projectFileUrl) {this.projectFileUrl = projectFileUrl;}

    public void updateDeadline(LocalDate deadline) {this.deadline = deadline;}

    public void updateTechStack(String techStack) {this.techStack = techStack;}

    public void updateSoftSkill(String softSkill) {this.softSkill = softSkill;}

    public void updateImportantQuestion(String importantQuestion) {this.importantQuestion = importantQuestion;}

    public void updateDescription(String description) {this.description = description;}

    public void updateLastModifiedAt(LocalDateTime lastModifiedAt) {this.lastModifiedAt = lastModifiedAt;}

}
