package org.minbros.chatbot.client;

import lombok.extern.slf4j.Slf4j;
import org.minbros.chatbot.dto.pinecone.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


@Component
@Slf4j
public class PineconeClient {
    private final WebClient webClient;

    public PineconeClient(@Value("${pinecone-api-key}") String pineconeAPIKey) {
        this.webClient = WebClient.builder()
                .baseUrl("https://sidae-bot-db-7y2rpah.svc.apw5-4e34-81fa.pinecone.io")
                .defaultHeader("Api-Key", pineconeAPIKey)
                .build();
    }

    public Mono<UpsertResponse> upsert(UpsertRequest request) {
        return webClient.post()
                .uri("/vectors/upsert")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(UpsertResponse.class);
    }

    public Mono<QueryResponse> query(QueryRequest request) {
        return webClient.post()
                .uri("/query")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(QueryResponse.class);
    }

    public Mono<FetchResponse> fetch(FetchRequest request) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/vectors/fetch")
                        .queryParam("ids", request.getIds())
                        .queryParam("namespace", request.getNamespace())
                        .build())
                .retrieve()
                .bodyToMono(FetchResponse.class);
    }
}
