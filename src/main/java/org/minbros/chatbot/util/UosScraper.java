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

    public String getCalendar() throws IOException {
        int currentYear = Year.now().getValue();
        String regex = String.format("^m%d(0[1-9]|1[1-2])c\\d+", currentYear);
        final String url = String.format
                (BASE_URL + "/korCalendarYear/list.do?list_id=CA1&year_code=%d&menuid=2000003003001000000", currentYear);

        Document document = Jsoup.connect(url).get();
        Elements elements = document.select(String.format("tr[id~=%s]", regex));
        StringBuilder result = new StringBuilder();
        for (Element element : elements) {
            result.append(element.text());
        }

        return result.toString();
    }
}
