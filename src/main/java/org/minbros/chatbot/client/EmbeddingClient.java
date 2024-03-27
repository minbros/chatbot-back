package org.minbros.chatbot.client;

import groovy.util.logging.Slf4j;
import org.minbros.chatbot.dto.openai.EmbedRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class EmbeddingClient {
    private final WebClient webClient;

    public EmbeddingClient() {
        String openaiAPIKey = System.getenv("OPENAI_API_KEY");

        this.webClient = WebClient.builder()
                .baseUrl("https://api.openai.com/v1/embeddings")
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + openaiAPIKey)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    @SuppressWarnings("unchecked")
    public List<Double> embedText(EmbedRequest request) {
        ArrayList<Map<String, List<Double>>> result = webClient.post()
                .bodyValue(request)
                .retrieve()
                .bodyToMono(Map.class)
                // Map의 value로 지정한 type에 해당되지 않는 값들도, 해당 값들을 따로 변수에
                // 저장하지 않으면 에러가 발생하지 않고 코드가 정상적으로 실행됨
                // 만약 지정한 value의 type에 맞지 않는 값을 저장하려고 할 경우 ClassCastException이 발생함
                .map(body -> (ArrayList<Map<String, List<Double>>>) body.get("data"))
                .block();

        if (result == null) {
            throw new NullPointerException("Response data was null.");
        }

        return result.get(0).get("embedding");
    }
}
