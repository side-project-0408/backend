package com.example.backend.repository;

import com.example.backend.domain.TokenBlackList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface JwtRepository extends JpaRepository<TokenBlackList, Long> {

    @Query("SELECT t FROM token t WHERE t.refreshToken = :token")
    Optional<TokenBlackList> findByToken(String token);

}
