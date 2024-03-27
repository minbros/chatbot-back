package org.minbros.chatbot.client;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PineconeClientTest {
    @Autowired
    private PineconeClient pineconeClient;

    @Test
    @DisplayName("Pinecone upsert 테스트")
    void upsertTest() {
        String response = pineconeClient.upsert("서울시립대는 등록금이 매우 저렴합니다.", "test");
        System.out.println(response);
        assertNotNull(response);
    }
}