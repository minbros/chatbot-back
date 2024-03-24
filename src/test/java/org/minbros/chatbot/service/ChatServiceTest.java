package org.minbros.chatbot.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.minbros.chatbot.repository.ChatRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("ALL")
@SpringBootTest
class ChatServiceTest {
    private static final Logger logger = LoggerFactory.getLogger(ChatServiceTest.class);

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private ChatService chatService;

    @Autowired
    private VectorStore vectorStore;

    @AfterEach
    public void afterEach() {
//        chatRepository.deleteAll();
    }

    @Test
    @DisplayName("챗봇 응답 출력")
    void testChatSaving() {
        String message = "How can I use chatgpt?";
        String response = chatService.generate(message).get("generation");
        assertNotNull(response);
    }

    @Test
    @DisplayName("Pinecone에 데이터 저장")
    void addToPinecone() {
        List<Document> documents = List.of(
                new Document("서울시립대학교의 대표 캐릭터는 이루매입니다.")
        );
        vectorStore.add(documents);
        assertNotNull(documents);
    }

    @Test
    @DisplayName("Pinecone 데이터에 쿼리")
    void queryPinecone() {
        String message = "What is the representative character of the municipal university?";
        List<Document> results = vectorStore.similaritySearch(message);
        String result = results.getFirst().getContent();
        System.out.println(results);
        System.out.println(result);
        assertNotNull(result);
    }
}