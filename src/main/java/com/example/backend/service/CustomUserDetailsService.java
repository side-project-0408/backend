package com.example.backend.service;

import com.example.backend.domain.User;
import com.example.backend.dto.security.CustomUserDetails;
import com.example.backend.repository.people.PeopleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final PeopleRepository peopleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }

    public UserDetails loadUserByUserId(Long userId) throws IllegalArgumentException {
        User user = peopleRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("해당 유저가 존재하지 않습니다. user_id = " + userId));
        return new CustomUserDetails(user);
    }

}
