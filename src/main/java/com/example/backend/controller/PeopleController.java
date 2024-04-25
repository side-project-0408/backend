package com.example.backend.controller;

import com.example.backend.common.response.CommonApiResponse;
import com.example.backend.dto.request.people.PeopleSearchDto;
import com.example.backend.dto.response.people.PeopleDetailResponseDto;
import com.example.backend.dto.response.people.PeopleResponseDto;
import com.example.backend.repository.PeopleRepository;
import com.example.backend.service.PeopleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PeopleController {

    private final PeopleRepository peopleRepository;

    @GetMapping("/peoples")
    public CommonApiResponse<List<PeopleResponseDto>> getPeoples(@ModelAttribute PeopleSearchDto dto) {

        return new CommonApiResponse<>("success", peopleRepository.findPeoples(dto));
    }

    @GetMapping("/peoples/{peopleId}")
    public CommonApiResponse<PeopleDetailResponseDto> getPeopleDetail(@PathVariable("peopleId") Integer peopleId) {


        return new CommonApiResponse<>("success", null);
    }

    @GetMapping("/peoples/hot")
    public CommonApiResponse<?> getHotPeoples() {


        return new CommonApiResponse<>("success", null);
    }
}
