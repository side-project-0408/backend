package com.example.backend.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

//TODO jwt 토큰 저장소 엑세스 토큰, 리프레시 토큰, 만료일,
@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Jwt {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(name = "token_type")
    private String tokenType;

    @Column(name = "access_token")
    private String accessToken;

    @Column(name = "refresh_token")
    private String refreshToken;



}
