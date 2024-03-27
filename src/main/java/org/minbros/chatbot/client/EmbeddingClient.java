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
        ArrayList<Map<String, Object>> result = webClient.post()
                .bodyValue(request)
                .retrieve()
                .bodyToMono(Map.class)
                .map(body -> (ArrayList<Map<String, Object>>) body.get("data"))
                .block();

        if (result == null) {
            throw new NullPointerException("Response data was null.");
        }

        return (List<Double>) result.get(0).get("embedding");
    }
}
