package com.example.backend.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "token")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TokenBlackList {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(name = "refresh_token")
    private String refreshToken;

}
