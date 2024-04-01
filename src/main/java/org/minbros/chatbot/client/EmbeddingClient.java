package org.minbros.chatbot.client;

import groovy.util.logging.Slf4j;
import org.minbros.chatbot.dto.openai.EmbedRequest;
import org.minbros.chatbot.dto.openai.EmbedResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


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

    public Mono<EmbedResponse> embed(EmbedRequest request) {
        return webClient.post()
                .bodyValue(request)
                .retrieve()
                .bodyToMono(EmbedResponse.class);
    }
}
