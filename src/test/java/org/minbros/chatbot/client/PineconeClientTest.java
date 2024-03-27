package org.minbros.chatbot.client;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.minbros.chatbot.dto.pinecone.request.FetchRequest;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PineconeClientTest {
    private PineconeClient pineconeClient;

    @Test
    @DisplayName("Pinecone query 테스트")
    void fetchTest() {
        List<String> ids;
        FetchRequest request = new FetchRequest();
    }
}