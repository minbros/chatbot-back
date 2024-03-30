package org.minbros.chatbot.util;

import org.minbros.chatbot.client.EmbeddingClient;
import org.minbros.chatbot.dto.openai.EmbedRequest;
import org.minbros.chatbot.dto.pinecone.PineconeVector;
import org.minbros.chatbot.dto.pinecone.request.UpsertRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PineconeRequestGenerator {
    private final EmbeddingClient embeddingClient;
    @Autowired
    public PineconeRequestGenerator(EmbeddingClient embeddingClient) {
        this.embeddingClient = embeddingClient;
    }

    public UpsertRequest toUpsertRequest(String message) {
        EmbedRequest embedRequest = new EmbedRequest(message);
        return embedRequestToUpsertRequest(embedRequest);
    }

    private UpsertRequest embedRequestToUpsertRequest(EmbedRequest embedRequest) {
        PineconeVector vector = PineconeVector.builder()
                .values(embeddingClient.embedText(embedRequest))
                .build();

        List<PineconeVector> vectorsList = new ArrayList<>();
        vectorsList.add(vector);

        return new UpsertRequest(vectorsList);
    }
}
