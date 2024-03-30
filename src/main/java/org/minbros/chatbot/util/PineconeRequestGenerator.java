package org.minbros.chatbot.util;

import org.minbros.chatbot.client.EmbeddingClient;
import org.minbros.chatbot.dto.openai.EmbedRequest;
import org.minbros.chatbot.dto.pinecone.request.UpsertRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class PineconeRequestGenerator {
    private final EmbeddingClient embeddingClient;

    private static final String NAMESPACE = "uos";

    @Autowired
    public PineconeRequestGenerator(EmbeddingClient embeddingClient) {
        this.embeddingClient = embeddingClient;
    }

    public UpsertRequest toUpsertRequest(String message) {
        return embedRequestToUpsertRequest(new EmbedRequest(message));
    }

    public UpsertRequest toUpsertRequest(String message, String id) {
        return embedRequestToUpsertRequest(new EmbedRequest(message), id);
    }

    private UpsertRequest embedRequestToUpsertRequest(EmbedRequest embedRequest) {
        UpsertRequest.Vector vector = UpsertRequest.Vector.builder()
                .values(embeddingClient.embedText(embedRequest))
                .metadata(Map.of("content", embedRequest.getInput()))
                .build();

        return getUpsertRequest(vector);
    }

    private UpsertRequest embedRequestToUpsertRequest(EmbedRequest embedRequest, String id) {
        UpsertRequest.Vector vector = UpsertRequest.Vector.builder()
                .id(id)
                .values(embeddingClient.embedText(embedRequest))
                .metadata(Map.of("content", embedRequest.getInput()))
                .build();

        return getUpsertRequest(vector);
    }

    private static UpsertRequest getUpsertRequest(UpsertRequest.Vector vector) {
        List<UpsertRequest.Vector> vectorsList = new ArrayList<>();
        vectorsList.add(vector);

        return new UpsertRequest(vectorsList, NAMESPACE);
    }
}
