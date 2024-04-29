package com.example.backend.service;

import com.example.backend.domain.User;
import com.example.backend.dto.request.people.UpdateRequestDto;
import com.example.backend.repository.PeopleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class PeopleService {

    private final PeopleRepository peopleRepository;
    public String update(Long userId, UpdateRequestDto dto) {
        Optional<User> user = Optional.ofNullable(peopleRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("해당 사용자는 존재하지 않습니다.")));



        return "수정이 완료되었습니다.";
    }
}
