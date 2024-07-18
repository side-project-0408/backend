package com.example.backend.controller;

import com.example.backend.common.response.CommonApiResponse;
import com.example.backend.common.response.PageApiResponse;
import com.example.backend.domain.Project;
import com.example.backend.domain.User;
import com.example.backend.dto.request.people.UpdateUserRequestDto;
import com.example.backend.dto.request.project.ProjectRequestDto;
import com.example.backend.dto.request.project.ProjectSearchDto;
import com.example.backend.dto.response.people.PeopleDetailResponseDto;
import com.example.backend.repository.people.PeopleRepository;
import com.example.backend.repository.project.ProjectRepository;
import com.example.backend.service.AwsS3Service;
import com.example.backend.service.JwtService;
import com.example.backend.service.PeopleService;
import com.example.backend.service.ProjectService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequiredArgsConstructor
public class MyPageController {

    private final PeopleRepository peopleRepository;
    private final PeopleService peopleService;
    private final ProjectRepository projectRepository;
    private final ProjectService projectService;

    private final JwtService jwtService;

    private final AwsS3Service awsS3Service;

    //마이페이지 내 정보 조회
    @GetMapping("/users")
    public CommonApiResponse<PeopleDetailResponseDto> getUser(HttpServletRequest servletRequest) {

        // JWT 토큰에서 userId 추출
        Long userId = jwtService.getUserIdFromToken(servletRequest);

        User user = peopleRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("해당 사용자를 찾을 수 없습니다."));

        PeopleDetailResponseDto dto = new PeopleDetailResponseDto(user);
        return new CommonApiResponse<>(OK, dto);
    }

    //마이페이지 내 정보 수정
    @PatchMapping("/users")
    public CommonApiResponse<?> editUser(@RequestPart UpdateUserRequestDto dto,
                                         @RequestPart(required = false) MultipartFile file,
                                         HttpServletRequest servletRequest) throws IOException {

        Long userId = jwtService.getUserIdFromToken(servletRequest);

        return new CommonApiResponse<>(OK, peopleService.update(userId, dto, file));
    }

    //내가 작성한 프로젝트 수정
    @PatchMapping("/posts/{projectId}")
    public CommonApiResponse<?> updateProject(@PathVariable("projectId") Long projectId,
                                              @RequestPart ProjectRequestDto dto,
                                              @RequestPart (required = false) MultipartFile file,
                                              HttpServletRequest servletRequest) throws IOException {
        return new CommonApiResponse<>(OK, projectService.updateProject(projectId, dto, file, servletRequest));
    }


    //내가 작성한 프로젝트 삭제
    @DeleteMapping("/posts/{projectId}")
    public CommonApiResponse<?> deleteProject(@PathVariable("projectId") Long projectId, HttpServletRequest servletRequest) {
        return new CommonApiResponse<>(OK, projectService.deleteProject(projectId, servletRequest));
    }

    //내가 찜한 프로젝트 목록
    @GetMapping("/projects/favorite")
    public CommonApiResponse<?> getFavoriteProjects(@ModelAttribute ProjectSearchDto request, HttpServletRequest servletRequest) {
        return new CommonApiResponse<>(OK, projectService.findFavoriteProjects(servletRequest, request));
    }

    //내가 작성한 프로젝트 목록
    @GetMapping("/posts")
    public PageApiResponse<?> getMyProjects(@ModelAttribute ProjectSearchDto request, HttpServletRequest servletRequest) {
        return projectService.findMyProjects(servletRequest, request);
    }

    //인증 메일 보내기
    @PostMapping("/verificationCode")
    public CommonApiResponse<?> sendVerificationCode(@RequestParam String email) {
        return new CommonApiResponse<>(OK, peopleService.sendVerificationCode(email));
    }

    //인증 메일 확인
    @GetMapping("/verificationCode")
    public CommonApiResponse<?> checkVerificationCode(@RequestParam String email, @RequestParam String code) {
        return new CommonApiResponse<>(OK, peopleService.checkVerificationCode(email, code));
    }

    //닉네임 중복 확인
    @GetMapping("/users/nickname")
    public CommonApiResponse<?> checkNickname(@RequestParam String nickname) {
        return new CommonApiResponse<>(OK, peopleService.checkNickname(nickname));
    }

}
