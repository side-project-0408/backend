package com.example.backend.repository.people;

import com.example.backend.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PeopleRepository extends JpaRepository<User, Long>, PeopleRepositoryCustom {

    //User findUserById(Long userId);

}
