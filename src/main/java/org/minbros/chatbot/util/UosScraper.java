package org.minbros.chatbot.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Year;

@Component
public class UosScraper {
    private static final String BASE_URL = "https://www.uos.ac.kr";

    public String getCalendar() {
        int currentYear = Year.now().getValue();
        String regex = String.format("^m(%d|%d)(0[1-9]|1[1-2])c\\d+", currentYear, currentYear + 1);
        final String url = BASE_URL + "/korCalendarYear/list.do?list_id=CA1";

        Elements elements;
        try {
            Document document = Jsoup.connect(url).get();
            elements = document.select(String.format("tr[id~=%s]", regex)).select("td");
        } catch (IOException e) {
            throw new IllegalStateException("학사일정 추출 중 에러");
        }

        StringBuilder result = new StringBuilder();
        for (Element element : elements) {
            result.append(element.text()).append("\n");
        }

        return result.toString();
    }
}
