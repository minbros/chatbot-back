package org.minbros.chatbot.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.minbros.chatbot.dto.ResponseGenerateDto;
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
        ResponseGenerateDto generateDto = new ResponseGenerateDto()
                .builder()
                .message("How can I use chatgpt?")
                .build();
        String response = chatService.generate(generateDto).get("generation");
        assertNotNull(response);
    }
}