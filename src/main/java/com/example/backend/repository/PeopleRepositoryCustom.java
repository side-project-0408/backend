package com.example.backend.repository;

import com.example.backend.dto.request.people.PeopleSearchDto;
import com.example.backend.dto.response.people.PeopleResponseDto;

import java.util.List;

public interface PeopleRepositoryCustom {
    List<PeopleResponseDto> findPeoples(PeopleSearchDto dto);
}
