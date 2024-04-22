package com.example.backend.domain;


import jakarta.persistence.*;

import static jakarta.persistence.FetchType.LAZY;

@Entity
public class Recruit {

    @Id @GeneratedValue
    @Column(name = "recruit_id")
    private Long recruitId;

    @Column(name = "target_count")
    private int targetCount;

    @Column(name = "current_count")
    private int currentCount;

    @Column
    private String position;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "project_id")
    private Project project;
}
