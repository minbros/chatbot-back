package org.minbros.chatbot.client;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.minbros.chatbot.dto.pinecone.*;
import org.minbros.chatbot.util.PineconeRequestGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PineconeClientTest {
    @Autowired
    private PineconeClient pineconeClient;

    @Autowired
    private PineconeRequestGenerator pineconeRequestGenerator;

    @Test
    @DisplayName("Pinecone upsert 테스트")
    void upsertTest() {
        UpsertRequest request =
                pineconeRequestGenerator.toUpsertRequest("서울시립대학교의 학생회관의 식단에 관련된 질문", "diet", "학식");
        UpsertResponse response = pineconeClient.upsert(request).block();
        System.out.println(response);
        assertNotNull(response);
    }

    @Test
    @DisplayName("Pinecone query 테스트")
    void queryTest() {
        QueryRequest request = pineconeRequestGenerator.toQueryRequest("우리 학교 공지사항을 알려줘", 1);
        QueryResponse response = pineconeClient.query(request).block();
        System.out.println(response);
        assertNotNull(response);
    }

    @Test
    @DisplayName("Pinecone fetch 테스트")
    void fetchTest() {
        FetchRequest request = pineconeRequestGenerator.toFetchRequest("announcement");
        FetchResponse response = pineconeClient.fetch(request).block();
        System.out.println(response);
        assertNotNull(response);
    }
}