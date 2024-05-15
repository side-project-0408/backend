package com.example.backend.repository.people;

import com.example.backend.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PeopleRepository extends JpaRepository<User, Long>, PeopleRepositoryCustom {

    User findUserByUserId(Long userId);

    @Query("SELECT u FROM User u WHERE u.nickname = :nickname")
    User findByNickname(String nickname);

    User findBySocialId(String nickname);
}
