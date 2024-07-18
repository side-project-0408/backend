package com.example.backend.repository.people;

import com.example.backend.common.response.PageApiResponse;
import com.example.backend.dto.request.people.HotSearchDto;
import com.example.backend.dto.request.people.PeopleSearchDto;
import com.example.backend.dto.response.people.PeopleResponseDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PeopleRepositoryCustom {
    PageApiResponse<List<PeopleResponseDto>> findPeoples(PeopleSearchDto dto);

    List<PeopleResponseDto> findHotPeoples(HotSearchDto dto);

    PageApiResponse<List<PeopleResponseDto>> findFavoritePeoples(Long peopleId, Pageable pageable);
}
