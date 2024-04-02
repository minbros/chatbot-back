package org.minbros.chatbot.client;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UOSClientTest {
    @Autowired
    private UosWiseClient uosClient;

    @Test
    @DisplayName("Wise 학사일정 및 공지사항 출력 테스트")
    void getAnnouncement() {
        String response = uosClient.getAnnouncement().block();
        System.out.println(response);
        assertNotNull(response);
    }
}