package org.minbros.chatbot.client;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.minbros.chatbot.dto.openai.EmbedRequest;
import org.minbros.chatbot.dto.openai.EmbedResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EmbeddingClientTest {
    @Autowired
    private EmbeddingClient embeddingClient;

    @Test
    @DisplayName("텍스트 임베딩 테스트")
    void textEmbeddingTest() {
        EmbedResponse result = embeddingClient.embed(new EmbedRequest("안녕하세요"));
        System.out.println(result);
        assertNotNull(result);
    }
}