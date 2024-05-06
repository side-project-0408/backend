package com.example.backend.service;

import com.example.backend.domain.Proposal;
import com.example.backend.domain.User;
import com.example.backend.repository.people.ProposalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class ProposalService {

    private final ProposalRepository proposalRepository;

    public String save(long userId, String messageText) {
        //TODO receiveId 어떻게 받아올지 생각 (필요없으면 뺴기)
        Proposal proposal = Proposal.builder()
                .proposerUser(User.builder().userId(userId).build())
                .message(messageText)
                .createdAt(LocalDateTime.now()).build();

        proposalRepository.save(proposal);

        return "제안 저장 완료";
    }
}
