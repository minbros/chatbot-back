package org.minbros.chatbot.client;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.minbros.chatbot.dto.openai.EmbedRequest;
import org.minbros.chatbot.dto.pinecone.request.FetchRequest;
import org.minbros.chatbot.dto.pinecone.request.QueryRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PineconeClientTest {
    @Autowired
    private PineconeClient pineconeClient;

    @Autowired
    private EmbeddingClient embeddingClient;

    @Test
    @DisplayName("Pinecone upsert 테스트")
    void upsertTest() {
        String response = pineconeClient.upsert("서울시립대는 등록금이 매우 저렴합니다.");
        System.out.println(response);
        assertNotNull(response);
    }

    @Test
    @DisplayName("Pinecone query 테스트")
    void queryTest() {
        QueryRequest request = QueryRequest.builder()
                .topK(2)
                .vector(embeddingClient.embedText(new EmbedRequest("이루매")))
                .namespace("uos")
                .build();

        String response = pineconeClient.query(request);
        System.out.println(response);
        assertNotNull(response);
    }

    @Test
    @DisplayName("Pinecone fetch 테스트")
    void fetchTest() {
        List<String> ids = new ArrayList<>();
        ids.add("0788cb0b-2039-446f-88bf-9186670eb09e");

        FetchRequest request = FetchRequest.builder()
                .ids(ids)
                .namespace("uos")
                .build();

        String response = pineconeClient.fetch(request);
        System.out.println(response);
        assertNotNull(response);
    }
}