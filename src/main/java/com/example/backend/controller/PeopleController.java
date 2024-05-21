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
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
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

    @GetMapping("/users/favorite/{userId}")
    public CommonApiResponse<List<PeopleResponseDto>> getFavoritePeoples(@PathVariable("peopleId") Long peopleId,
                                                                         @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.ASC) Pageable pageable) {
        return new CommonApiResponse<>("success", peopleRepository.findFavoritePeoples(peopleId, pageable));
    }

    @PostMapping("/proposal/send")
    public CommonApiResponse<?> sendProposal(HttpServletRequest servletRequest) throws Exception {

        long userId = jwtService.getUserIdFromToken(servletRequest);

        proposalService.sendProposal(userId);
        return new CommonApiResponse<>("success", "제안 신청이 성공하였습니다.");
    }
}
