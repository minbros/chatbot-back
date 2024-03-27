package org.minbros.chatbot.client;

import lombok.extern.slf4j.Slf4j;
import org.minbros.chatbot.dto.openai.EmbedRequest;
import org.minbros.chatbot.dto.pinecone.PineconeVector;
import org.minbros.chatbot.dto.pinecone.request.FetchRequest;
import org.minbros.chatbot.dto.pinecone.request.UpsertRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class PineconeClient {
    private final EmbeddingClient embeddingClient;

    private final WebClient webClient;

    private String upsertNamespace = "uos";

    @Autowired
    public PineconeClient(EmbeddingClient embeddingClient) {
        String pineconeAPIKey = System.getenv("PINECONE_API_KEY");

        this.embeddingClient = embeddingClient;
        this.webClient = WebClient.builder()
                .baseUrl("https://sidae-bot-db-7y2rpah.svc.apw5-4e34-81fa.pinecone.io")
                .defaultHeader("Api-Key", pineconeAPIKey)
                .build();
    }

    public String upsert(String message) throws IllegalArgumentException {
        return requestClientToUpsert(message);
    }

    public String upsert(String message, String namespace) throws IllegalArgumentException {
        upsertNamespace = namespace;
        return requestClientToUpsert(message);
    }

    public String fetch(FetchRequest request) throws IllegalArgumentException {
        String uri = request.toUri();
        log.info("Uri converted due to fetch method: {}", uri);

        return webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    private List<PineconeVector> getVectorsToUpsert(String message) {
        List<Double> embedded = embeddingClient.embedText(new EmbedRequest(message));
        log.info("Embedded value: {}", embedded);

        Map<String, String> metadata = Map.of("content", message);
        new PineconeVector();
        PineconeVector vector = PineconeVector.builder().values(embedded).metadata(metadata).build();

        List<PineconeVector> vectors = new ArrayList<>();
        vectors.add(vector);

        return vectors;
    }

    private String requestClientToUpsert(String message) {
        UpsertRequest request = new UpsertRequest(getVectorsToUpsert(message), upsertNamespace);

        return webClient.post()
                .uri("/vectors/upsert")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
