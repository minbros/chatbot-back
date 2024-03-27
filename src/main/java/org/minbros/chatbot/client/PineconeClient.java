package org.minbros.chatbot.client;

import lombok.extern.slf4j.Slf4j;
import org.minbros.chatbot.dto.pinecone.request.FetchRequest;
import org.minbros.chatbot.dto.pinecone.request.QueryRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class PineconeClient {
    private final String pineconeAPIKey = System.getenv("PINECONE_API_KEY");

    private final EmbeddingClient embeddingClient;

    private final WebClient webClient;

    @Autowired
    public PineconeClient(EmbeddingClient embeddingClient) {
        this.embeddingClient = embeddingClient;
        this.webClient = WebClient.create("https://sidae-bot-db-7y2rpah.svc.apw5-4e34-81fa.pinecone.io");
    }

    public Mono<String> fetch(FetchRequest request) throws IllegalArgumentException {
        String uri = request.toUri();
        log.info("변환된 uri: {}", uri);

        return webClient.get()
                .uri(uri)
                .header("Authorization", pineconeAPIKey)
                .retrieve()
                .bodyToMono(String.class);
    }

    public Mono<String> query(QueryRequest request) throws IllegalArgumentException {
        return null;
    }
}
