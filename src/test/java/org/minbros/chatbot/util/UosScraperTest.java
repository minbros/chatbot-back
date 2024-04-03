package org.minbros.chatbot.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UosScraperTest {
    @Autowired
    private UosScraper uosScraper;

    @Test
    @DisplayName("학사일정 스크랩 테스트")
    void scrapCalendar() {
        String calendar = uosScraper.getCalendar();
        System.out.println(calendar);
        assertNotNull(calendar);
    }

    @Test
    @DisplayName("식단표 스크랩 테스트")
    void scrapDiet() {
        String diet = uosScraper.getDiet(RestaurantLocation.NATURAL_SCIENCE);
        System.out.println(diet);
        assertNotNull(diet);
    }
}