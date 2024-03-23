package org.minbros.chatbot.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.minbros.chatbot.repository.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("ALL")
@SpringBootTest
class ChatServiceTest {

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private ChatService chatService;

    @AfterEach
    public void afterEach() {
//        chatRepository.deleteAll();
    }

    @Test
    @DisplayName("챗봇 응답 출력")
    void testChatSaving() {
        String message = "How can I use chatgpt?";
        String response = chatService.generate(message).get("generation");

        StringBuilder sb = new StringBuilder(response);
        int i = 0;
        while (i + 50 < sb.length() && (i = sb.lastIndexOf(" ", i + 50)) != -1) {
            sb.replace(i, i + 1, "\n");
        }
        System.out.println(sb.toString());
    }
}