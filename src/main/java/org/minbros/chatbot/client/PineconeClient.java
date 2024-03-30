package org.minbros.chatbot.client;

import lombok.extern.slf4j.Slf4j;
import org.minbros.chatbot.dto.pinecone.request.FetchRequest;
import org.minbros.chatbot.dto.pinecone.request.QueryRequest;
import org.minbros.chatbot.dto.pinecone.request.UpsertRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;


@Component
@Slf4j
@SuppressWarnings("unchecked")
public class PineconeClient {
    private final WebClient webClient;

    public PineconeClient() {
        String pineconeAPIKey = System.getenv("PINECONE_API_KEY");

        this.webClient = WebClient.builder()
                .baseUrl("https://sidae-bot-db-7y2rpah.svc.apw5-4e34-81fa.pinecone.io")
                .defaultHeader("Api-Key", pineconeAPIKey)
                .build();
    }

    public String upsert(UpsertRequest request) {
        return webClient.post()
                .uri("/vectors/upsert")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    // Query 실행 시, 출력 결과로 가장 유사도가 높은 데이터의 id만 나오도록 설정함
    public String query(QueryRequest request) {
        ArrayList<Map<String, String>> response =  webClient.post()
                .uri("/query")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(Map.class)
                .map(body -> (ArrayList<Map<String, String>>) body.get("matches"))
                .block();

        if (response == null || response.isEmpty()) {
            throw new IllegalArgumentException("Response is null or empty(query)");
        }

        Map<String, String> firstElement = response.get(0);
        if (firstElement == null) {
            throw new IllegalArgumentException("First element of response is null(query)");
        }

        return firstElement.get("id");
    }

    // Fetch 실행 시, fetch된 데이터의 metadata를 Map 형태로 출력하게 설정함
    public Map<String, String> fetch(FetchRequest request) {
        String uri = request.toUri();
        log.info("Uri converted due to fetch method: {}", uri);

        Map<String, Map<String, Map<String, String>>> response = webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(Map.class)
                .map(body -> (Map<String, Map<String, Map<String, String>>>) body.get("vectors"))
                .block();

        if (response == null || response.isEmpty()) {
            throw new IllegalArgumentException("Response is null or empty(fetch)");
        }

        Map<String, Map<String, String>> firstElement = response.get(request.getIds().get(0));
        if (firstElement == null) {
            throw new IllegalArgumentException("First element of response is null(fetch)");
        }

        return firstElement.get("metadata");
    }
}
