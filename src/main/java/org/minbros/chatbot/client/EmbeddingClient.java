package org.minbros.chatbot.client;

import groovy.util.logging.Slf4j;
import lombok.Getter;
import org.minbros.chatbot.dto.openai.EmbedRequest;
import org.minbros.chatbot.dto.openai.EmbedResponse;
import org.springframework.ai.document.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


@Component
@Getter
@Slf4j
public class EmbeddingClient {
    private final WebClient webClient;

    public EmbeddingClient(@Value("${openai-api-key}") String openaiAPIKey) {
        this.webClient = WebClient.builder()
                .baseUrl("https://api.openai.com/v1/embeddings")
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + openaiAPIKey)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    public Mono<EmbedResponse> embed(EmbedRequest request) {
        return webClient.post()
                .bodyValue(request)
                .retrieve()
                .bodyToMono(EmbedResponse.class);
    }

    public Mono<EmbedResponse> embed(Document document) {
        EmbedRequest request = new EmbedRequest(document.getContent());
        return embed(request);
    }
}
