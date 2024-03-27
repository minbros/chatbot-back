package org.minbros.chatbot.client;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.minbros.chatbot.dto.openai.EmbedRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EmbeddingClientTest {
    @Autowired
    private EmbeddingClient embeddingClient;

    @Test
    @DisplayName("텍스트 임베딩 테스트")
    void textEmbeddingTest() {
        Mono<String> mono = embeddingClient.embedText(new EmbedRequest("안녕하세요"));
        String result = mono.block();
        System.out.println(result);
        assertNotNull(result);
    }

}