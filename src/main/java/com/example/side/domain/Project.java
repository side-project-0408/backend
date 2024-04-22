package com.example.side.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static jakarta.persistence.FetchType.*;

@Entity
public class Project {

    @Id @GeneratedValue
    @Column(name = "project_id")
    private Long projectId;

    private String title;

    @Column(name = "file_url")
    private String projectFileUrl;

    private LocalDateTime deadline;

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

    @ElementCollection
    @CollectionTable(name = "projectLike", joinColumns = @JoinColumn(name = "project_id"))
    @Column(name = "user_id")
    private Set<Integer> projectLike = new HashSet<>();

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "created_id")
    private User user;

}
