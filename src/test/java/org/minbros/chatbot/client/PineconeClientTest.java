package org.minbros.chatbot.client;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.minbros.chatbot.dto.openai.EmbedRequest;
import org.minbros.chatbot.dto.pinecone.request.FetchRequest;
import org.minbros.chatbot.dto.pinecone.request.QueryRequest;
import org.minbros.chatbot.dto.pinecone.request.UpsertRequest;
import org.minbros.chatbot.util.PineconeRequestGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PineconeClientTest {
    @Autowired
    private PineconeClient pineconeClient;

    @Autowired
    private EmbeddingClient embeddingClient;

    @Autowired
    private PineconeRequestGenerator pineconeRequestGenerator;

    @Test
    @DisplayName("Pinecone upsert 테스트")
    void upsertTest() {
        UpsertRequest request =
                pineconeRequestGenerator.toUpsertRequest("서울시립대학교의 공지사항에 관련된 질문", "announcement");
        String response = pineconeClient.upsert(request);

        System.out.println(response);
        assertNotNull(response);
    }

    @Test
    @DisplayName("Pinecone query 테스트")
    void queryTest() {
        QueryRequest request = QueryRequest.builder()
                .topK(1)
                .vector(embeddingClient.embedText(new EmbedRequest("우리 학교 공지사항을 알려줘")))
                .namespace("uos")
                .build();

        Object response = pineconeClient.query(request);
        System.out.println(response);
        assertNotNull(response);
    }

    @Test
    @DisplayName("Pinecone fetch 테스트")
    void fetchTest() {
        List<String> ids = new ArrayList<>();
        ids.add("announcement");

        FetchRequest request = FetchRequest.builder()
                .ids(ids)
                .namespace("uos")
                .build();

        Object response = pineconeClient.fetch(request);
        System.out.println(response);
        assertNotNull(response);
    }
}