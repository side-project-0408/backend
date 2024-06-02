package com.example.backend.service;

import com.amazonaws.services.s3.AmazonS3;
import com.example.backend.domain.User;
import com.example.backend.dto.request.people.UpdateUserRequestDto;
import com.example.backend.repository.people.PeopleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Transactional
public class PeopleService {

    private final PeopleRepository peopleRepository;
    private final AmazonS3 amazonS3;

    private final AwsS3Service awsS3Service;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public String update(Long userId,
                         @RequestPart UpdateUserRequestDto dto,
                         @RequestPart(required = false) MultipartFile file) throws IOException {
        User user = peopleRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("해당 사용자는 존재하지 않습니다."));

        String fileUrl;
        if (file != null && !file.isEmpty()) {
            // 기존 파일 삭제
            awsS3Service.deleteFileFromS3(user.getUserFileUrl());
            // 새 파일 업로드
            fileUrl = awsS3Service.upload(file);
        } else {
            // 기존 파일 URL 유지
            fileUrl = user.getUserFileUrl();
        }

        user.updateUser(dto.getNickname(),
                dto.getPosition(),
                fileUrl,
                dto.isEmploymentStatus(),
                dto.getTechStack(),
                dto.getSoftSkill(),
                dto.getYear(),
                dto.getLinks(),
                dto.getContent(),
                dto.isAlarmStatus(),
                dto.getEmail());

        peopleRepository.save(user);

        return "수정이 완료되었습니다.";
    }
}
