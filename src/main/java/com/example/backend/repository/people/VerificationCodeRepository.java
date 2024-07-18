package com.example.backend.repository.people;

import com.example.backend.domain.VerificationCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface VerificationCodeRepository extends JpaRepository<VerificationCode, String> {

    @Query("SELECT c FROM code c WHERE c.email = :email")
    VerificationCode findByEmail(String email);

}
