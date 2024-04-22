package com.example.backend.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.LAZY;

@Entity
public class Proposal {
    @Id
    @GeneratedValue
    @Column(name = "proposal_id")
    private Long proposalId;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "proposer_id")
    private User proposerUser;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "recieved_id")
    private User recievedUser;

    private String message;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

}
