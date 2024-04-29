package com.example.backend.repository.people;

import com.example.backend.dto.request.people.HotSearchDto;
import com.example.backend.dto.request.people.PeopleSearchDto;
import com.example.backend.dto.response.people.PeopleResponseDto;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.*;

public interface PeopleRepositoryCustom {
    List<PeopleResponseDto> findPeoples(PeopleSearchDto dto);
    List<PeopleResponseDto> findHotPeoples(HotSearchDto dto);
    List<PeopleResponseDto> findFavoritePeoples(Long peopleId, Pageable pageable);
}
