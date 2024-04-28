package com.example.backend.controller;

import com.example.backend.common.response.CommonApiResponse;
import com.example.backend.domain.Project;
import com.example.backend.domain.User;
import com.example.backend.dto.request.people.UpdateRequestDto;
import com.example.backend.dto.response.people.PeopleDetailResponseDto;
import com.example.backend.repository.PeopleRepository;
import com.example.backend.repository.ProjectRepository;
import com.example.backend.service.PeopleService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class MyPageController {

    private final PeopleRepository peopleRepository;
    private final PeopleService peopleService;
    private final ProjectRepository projectRepository;

    //마이페이지 내 정보 조회
    @GetMapping("/users/{userId}")
    public CommonApiResponse<PeopleDetailResponseDto> getUser(@PathVariable("userId") Long userId) {
        User user = peopleRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("해당 사용자를 찾을 수 없습니다."));

        PeopleDetailResponseDto dto = new PeopleDetailResponseDto(user);
        return new CommonApiResponse<>("success", dto);
    }

    //마이페이지 내 정보 수정
    @PatchMapping("/users/{userId}")
    public CommonApiResponse<?> editUser(@PathVariable("userId") Long userId, @RequestBody UpdateRequestDto dto) {



        return new CommonApiResponse<>("success", peopleService.update(userId, dto));
    }

    //내가 작성한 프로젝트 수정
    @PatchMapping("/posts/{userId}")
    public CommonApiResponse<?> editPost(@PathVariable("userId") Long userId,
                                         @PathVariable("projectId") Long projectId) {

        return new CommonApiResponse<>("success", null);
    }

    //내가 작성한 프로젝트 삭제
    @DeleteMapping("/posts/{userId}")
    public CommonApiResponse<?> deletePost(@PathVariable("userId") Long userId,
                                         @PathVariable("projectId") Long projectId) {

        Project project = projectRepository.findByUserIdAndProjectId(userId, projectId)
                .orElseThrow(() -> new RuntimeException("프로젝트를 찾을 수 없습니다."));
        projectRepository.delete(project);

        return new CommonApiResponse<>("success", "삭제되었습니다.");
    }
}
