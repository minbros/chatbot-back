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
        final String regex = String.format("^m(%d|%d)(0[1-9]|1[1-2])c\\d+", currentYear, currentYear + 1);
        final String url = BASE_URL + "/korCalendarYear/list.do?list_id=CA1";

        Elements elements;
        try {
            Document document = Jsoup.connect(url).get();
            elements = document.select(String.format("tr[id~=%s]", regex)).select("td");
        } catch (IOException e) {
            throw new IllegalStateException("학사일정 추출 중 에러");
        }

        return getText(elements);
    }

    public String getDiet(RestaurantLocation location) {
        final String menuId = "2000005006002000000";
        final String url = BASE_URL + String.format("/food/placeList.do?rstcde=%s&menuid=%s", location.getStringValue(), menuId);

        Elements elements;
        try {
            Document document = Jsoup.connect(url).get();
            elements = document.select("div#week").select("tr");
        } catch (IOException e) {
            throw new IllegalStateException("식단표 추출 중 에러");
        }

        return getText(elements);
    }

    private static String getText(Elements elements) {
        StringBuilder result = new StringBuilder();
        for (Element element : elements) {
            result.append(element.text()).append("\n");
        }

        return result.toString();
    }
}
