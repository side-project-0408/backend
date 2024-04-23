package com.example.backend.controller;

import com.example.backend.common.response.CommonApiResponse;
import com.example.backend.domain.User;
import com.example.backend.dto.request.people.PeopleSearchDto;
import com.example.backend.dto.response.PeopleResponseDto;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.*;

@RestController
public class PeopleController {

    @GetMapping("/peoples")
    public CommonApiResponse<List<PeopleResponseDto>> getPeoples(@ModelAttribute PeopleSearchDto dto) {

        return new CommonApiResponse<>("success",null);
    }

}
