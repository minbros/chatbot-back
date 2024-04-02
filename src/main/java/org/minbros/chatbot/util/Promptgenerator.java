package org.minbros.chatbot.util;

import lombok.RequiredArgsConstructor;
import org.minbros.chatbot.dto.openai.ChatRequest;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class Promptgenerator {
    private final UosScraper uosScraper;

    public String generateProperPrompt(Map<String, Object> metadata, ChatRequest request)  {
        StringBuilder stringBuilder = new StringBuilder();

        if (metadata.get("keyword").equals("학사일정")) {
            getCalendarPrompt(request, stringBuilder);
        }

        return stringBuilder.toString();
    }

    // 성능을 위해서 프롬프트를 영어로 대체하고, fine tuning도 고려해야 함
    private void getCalendarPrompt(ChatRequest request, StringBuilder stringBuilder) {
        stringBuilder.append(uosScraper.getCalendar());
        stringBuilder.append("\n" +
                "우리 학교(서울시립대학교)의 일정은 위와 같고, " +
                "사용자의 질문이 특정 일정이 언제인지 물어본 게 아니라면 전체 일정을 " +
                "요약해서 설명해줘. 사용자의 질문은 다음과 같아: ");
        stringBuilder.append(request.getMessage());
        stringBuilder.append("\n" +
                "그리고 마지막에는 해당 설명이 정확하지 않을 수 있으니 정확한 일정은 " +
                "\"https://www.uos.ac.kr/korCalendarYear/list.do?list_id=CA1\"에서 확인하라는 " +
                "말을 덧붙여줘");
    }
}
