package org.minbros.chatbot.client;

import lombok.extern.slf4j.Slf4j;
import org.minbros.chatbot.dto.pinecone.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;


@Component
@Slf4j
public class PineconeClient {
    private final WebClient webClient;

    public PineconeClient() {
        String pineconeAPIKey = System.getenv("PINECONE_API_KEY");

        this.webClient = WebClient.builder()
                .baseUrl("https://sidae-bot-db-7y2rpah.svc.apw5-4e34-81fa.pinecone.io")
                .defaultHeader("Api-Key", pineconeAPIKey)
                .build();
    }

    public UpsertResponse upsert(UpsertRequest request) {
        return webClient.post()
                .uri("/vectors/upsert")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(UpsertResponse.class)
                .block();
    }

    public QueryResponse query(QueryRequest request) {
        return webClient.post()
                .uri("/query")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(QueryResponse.class)
                .block();
    }

    public FetchResponse fetch(FetchRequest request) {
        String uri = request.toUri();
        log.info("Uri converted due to fetch method: {}", uri);

        return webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(FetchResponse.class)
                .block();
    }
}
