package com.example.backend.service;

import com.example.backend.domain.User;
import com.example.backend.dto.request.people.UpdateUserRequestDto;
import com.example.backend.repository.people.PeopleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PeopleService {

    private final PeopleRepository peopleRepository;
    public String update(Long userId, UpdateUserRequestDto dto) {
        User user = peopleRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("해당 사용자는 존재하지 않습니다."));

        user.updateUser(dto.getNickname(),
                dto.getPosition(),
                dto.getUserFileUrl(),
                dto.isEmploymentStatus(),
                dto.getTechStack(),
                dto.getSoftSkill(),
                dto.getImportantQuestion(),
                dto.getYear(),
                dto.getLinks(),
                dto.getContent(),
                dto.isAlarmStatus());

        peopleRepository.save(user);

        return "수정이 완료되었습니다.";
    }
}
