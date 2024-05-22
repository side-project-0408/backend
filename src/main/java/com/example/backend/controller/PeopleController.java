package com.example.backend.controller;

import com.example.backend.common.response.CommonApiResponse;
import com.example.backend.domain.User;
import com.example.backend.dto.request.people.HotSearchDto;
import com.example.backend.dto.request.people.PeopleSearchDto;
import com.example.backend.dto.response.people.PeopleDetailResponseDto;
import com.example.backend.dto.response.people.PeopleResponseDto;
import com.example.backend.repository.people.PeopleRepository;
import com.example.backend.repository.project.ProjectRepository;
import com.example.backend.service.JwtService;
import com.example.backend.service.KakaoMessageService;
import com.example.backend.service.ProposalService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PeopleController {

    private final PeopleRepository peopleRepository;
    private final ProjectRepository projectRepository;
    private final KakaoMessageService kakaoMessageService;
    private final ProposalService proposalService;
    private final JwtService jwtService;

    @GetMapping("/peoples")
    public CommonApiResponse<List<PeopleResponseDto>> getPeoples(@ModelAttribute PeopleSearchDto dto) {

        return new CommonApiResponse<>("success", peopleRepository.findPeoples(dto));
    }

    @GetMapping("/peoples/{peopleId}")
    public CommonApiResponse<PeopleDetailResponseDto> getPeopleDetail(@PathVariable("peopleId") Long peopleId) throws Exception {

        User user = peopleRepository.findById(peopleId)
                .orElseThrow(() -> new RuntimeException("해당 피플을 찾을 수 없습니다."));

        PeopleDetailResponseDto dto = new PeopleDetailResponseDto(user);

        return new CommonApiResponse<>("success", dto);
    }

    @GetMapping("/peoples/hot")
    public CommonApiResponse<List<PeopleResponseDto>> getHotPeoples(@ModelAttribute HotSearchDto dto) {
        return new CommonApiResponse<>("success", peopleRepository.findHotPeoples(dto));
    }

    @GetMapping("/users/favorite")
    public CommonApiResponse<List<PeopleResponseDto>> getFavoritePeoples(HttpServletRequest servletRequest,
                                                                         @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.ASC) Pageable pageable) {

        Long userId = jwtService.getUserIdFromToken(servletRequest);
        return new CommonApiResponse<>("success", peopleRepository.findFavoritePeoples(userId, pageable));
    }

    @PostMapping("/proposal/send/{peopleId}")
    public CommonApiResponse<?> sendProposal(@PathVariable("peopleId") Long receiver,
                                             HttpServletRequest servletRequest) throws Exception {

        Long proposer = jwtService.getUserIdFromToken(servletRequest); //보내는 사람

        proposalService.sendProposal(receiver, proposer);
        return new CommonApiResponse<>("success", "제안 신청이 성공하였습니다.");
    }
}
