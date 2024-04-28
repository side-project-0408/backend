package com.example.backend.service;

import com.example.backend.dto.request.people.UpdateRequestDto;
import org.springframework.stereotype.Service;

@Service
public class PeopleService {
    public String update(Long userId, UpdateRequestDto dto) {
        return "수정이 완료되었습니다.";
    }
}
