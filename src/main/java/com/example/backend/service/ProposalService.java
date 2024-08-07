package com.example.backend.service;

import com.example.backend.domain.Proposal;
import com.example.backend.domain.User;
import com.example.backend.repository.people.PeopleRepository;
import com.example.backend.repository.people.ProposalRepository;
import com.example.backend.repository.project.ProjectRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProposalService {

    private final ProposalRepository proposalRepository;
    private final ProjectRepository projectRepository;
    private final PeopleRepository peopleRepository;
    private final JavaMailSender javaMailSender;


    public String save(Long proposer, String content, Long receiver) {
        Proposal proposal = Proposal.builder()
                .proposerUser(User.builder().userId(proposer).build())
                .recievedUser(User.builder().userId(receiver).build())
                .message(content)
                .createdAt(LocalDateTime.now()).build();
        proposalRepository.save(proposal);
        return "제안 저장 완료";
    }

    public void sendProposal(Long receiver, Long proposer) throws MessagingException {
        //로그인한 사람이 작성한 프로젝트 게시글 아이디 목록
        List<Long> projectIds = projectRepository.getProjectIdsByUserId(proposer);

        String email = peopleRepository.findEmailByUserId(receiver);

        String from = "matchmate25@gmail.com"; //프로젝트 이메일로 변경 필요
        String to = email; //받는 사람 이메일 (클릭된 people 페이지 아이디로 이메일 구하기)
        String subject = "[매치메이트] 제안이 도착했습니다.";


        StringBuilder sb = new StringBuilder();
        sb.append("<html><body>");
        sb.append("<p>제안이 도착했습니다!</p>");

        if (projectIds == null || projectIds.isEmpty()) {
            sb.append("<p>제안한 사람이 작성한 프로젝트가 없습니다.</p>")
                    .append("<p>더 많은 프로젝트 목록 확인하기 -> <a href=\"http://localhost:8081/projects\">http://localhost:8081/projects</a></p>");
        } else {
            for (Long projectId : projectIds) {
                sb.append("<p>제안한 사람의 프로젝트를 확인하세요. -> ")
                        .append("<a href=\"http://localhost:8081/posts/")
                        .append(projectId)
                        .append("\">http://localhost:8081/posts/")
                        .append(projectId)
                        .append("</a></p>");
            }
        }

        sb.append("</body></html>");

        String content = sb.toString();

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

//        try {
            helper.setSubject(subject);
            helper.setText(content, true);
            helper.setFrom(from);
            helper.setTo(to);

            message.setHeader("Content-Type", "text/html; charset=UTF-8");
            message.setContent(content, "text/html; charset=UTF-8");
            javaMailSender.send(message);
//        } catch (Exception e) {
//            throw new RuntimeException("이메일을 전송할 수 없습니다.");
//        }
        save(proposer, content, receiver);
    }
}
