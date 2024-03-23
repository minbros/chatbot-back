package org.minbros.chatbot.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.minbros.chatbot.domain.Member;
import org.minbros.chatbot.repository.ChatRepository;
import org.minbros.chatbot.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SuppressWarnings("ALL")
@SpringBootTest
class ChatServiceTest {

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private ChatService chatService;

    @Autowired
    private MemberRepository memberRepository;

    @AfterEach
    public void afterEach() {
//        chatRepository.deleteAll();
//        memberRepository.deleteAll();
    }

    @Test
    void 챗봇과_채팅() {
        Member member = new Member();
        member.setUserId("Kim");
        memberRepository.save(member);
        String message = "How can I use chatgpt?";
        String response = chatService.generate(message).get("generation");

        StringBuilder sb = new StringBuilder(response);
        int i = 0;
        while (i + 50 < sb.length() && (i = sb.lastIndexOf(" ", i + 50)) != -1) {
            sb.replace(i, i + 1, "\n");
        }
        System.out.println(sb.toString());
    }

    @Test
    void clearAll() {
        chatRepository.deleteAll();
        memberRepository.deleteAll();
    }
}