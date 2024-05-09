package com.example.backend.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.example.backend.domain.User;
import com.example.backend.dto.request.people.UpdateUserRequestDto;
import com.example.backend.repository.people.PeopleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Transactional
public class PeopleService {

    private final PeopleRepository peopleRepository;
    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;
    public String update(Long userId, UpdateUserRequestDto dto) throws IOException {
        User user = peopleRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("해당 사용자는 존재하지 않습니다."));

        String originalFilename = dto.getUserFile().getOriginalFilename();
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(dto.getUserFile().getSize());
        metadata.setContentType(dto.getUserFile().getContentType());

        amazonS3.putObject(bucket, originalFilename, dto.getUserFile().getInputStream(), metadata);

        user.updateUser(dto.getNickname(),
                dto.getPosition(),
                dto.getUserFileUrl(),
                dto.isEmploymentStatus(),
                dto.getTechStack(),
                dto.getSoftSkill(),
                dto.getYear(),
                dto.getLinks(),
                dto.getContent(),
                dto.isAlarmStatus());

        peopleRepository.save(user);

        return "수정이 완료되었습니다.";
    }
}
