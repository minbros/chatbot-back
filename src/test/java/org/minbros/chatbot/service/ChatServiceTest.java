package org.minbros.chatbot.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.minbros.chatbot.dto.openai.ChatRequest;
import org.minbros.chatbot.repository.ChatRepository;
import org.springframework.ai.chat.ChatResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;


import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("ALL")
@SpringBootTest
class ChatServiceTest {
    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private ChatService chatService;

    @Test
    @DisplayName("챗봇 응답 출력")
    void testChatSaving() {
        ChatRequest generateDto = new ChatRequest("2학기 휴학 신청기간이 언제야?");
        String response = chatService.generate(generateDto).get("generation");
        System.out.println(response);
        assertNotNull(response);
    }

    @Test
    @DisplayName("챗봇 스트림 응답 출력")
    void generateStreamTest() {
        ChatRequest request = new ChatRequest("나 너무 피곤해");
        Flux<ChatResponse> responseFlux = chatService.generateStream(request);
        responseFlux.subscribe(data -> System.out.print(data.getResult().getOutput().getContent()));
        String content = responseFlux.blockLast().getResult().getOutput().getContent();
        assertNull(content);    // Response의 마지막은 null로 나타남
    }
}