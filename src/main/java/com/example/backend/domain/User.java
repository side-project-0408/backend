package com.example.backend.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "access_token")
    private String accessToken;

    @Column(name = "refresh_token")
    private String refreshToken;

    private String nickname;

    private String position;

    @Column(name = "file_url")
    private String userFileUrl;

    @Column(name = "employment_status")
    private String employmentStatus;

    @Column(name = "tech_stack")
    private String techStack;

    @Column(name = "soft_skill")
    private String softSkill;

    @Column(name = "important_question")
    private String importantQuestion;

    private String year;

    private String links;

    @Column(name = "view_count")
    private int viewCount;

    @Column(name = "favorite_count")
    private int favoriteCount;

    @Lob
    private String content;

    @Column(name = "alarm_status")
    private boolean alarmStatus;

    @Column(name = "last_modified_at")
    private LocalDateTime lastModifiedAt;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Builder.Default
    @ElementCollection
    @CollectionTable(name = "userLike", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "favorite_id")
    private Set<Integer> userLike = new HashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "user")
    private List<Project> projects = new ArrayList<>();

}
