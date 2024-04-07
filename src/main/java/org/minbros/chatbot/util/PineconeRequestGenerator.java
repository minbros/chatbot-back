package org.minbros.chatbot.util;

import org.minbros.chatbot.client.EmbeddingClient;
import org.minbros.chatbot.dto.openai.EmbedRequest;
import org.minbros.chatbot.dto.openai.EmbedResponse;
import org.minbros.chatbot.dto.pinecone.FetchRequest;
import org.minbros.chatbot.dto.pinecone.QueryRequest;
import org.minbros.chatbot.dto.pinecone.UpsertRequest;
import org.minbros.chatbot.dto.pinecone.Embedding;
import org.springframework.ai.document.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Component
public class PineconeRequestGenerator {
    // 텍스트 같은 request의 value에 직접적으로 포함되지 않는 데이터나
    // request에 사용될 수 있는 value 중 자주 이용되는 데이터 조합을 입력받아서
    // Request 형식으로 변환시켜주는 객체
    private final EmbeddingClient embeddingClient;

    private static final String DEFAULT_NAMESPACE = "";

    @Autowired
    public PineconeRequestGenerator(EmbeddingClient embeddingClient) {
        this.embeddingClient = embeddingClient;
    }

    public UpsertRequest toUpsertRequest(Document document) {
        List<Embedding> embeddings = new ArrayList<>();
        addEmbeddingValues(document, embeddings);

        return UpsertRequest.builder()
                .vectors(embeddings)
                .namespace(DEFAULT_NAMESPACE)
                .build();
    }

    public UpsertRequest toUpsertRequest(List<Document> documents) {
        List<Embedding> embeddings = new ArrayList<>();
        for (Document document : documents) { addEmbeddingValues(document, embeddings); }

        return UpsertRequest.builder()
                .vectors(embeddings)
                .namespace(DEFAULT_NAMESPACE)
                .build();
    }

    public QueryRequest toQueryRequest(String message, int topK) {
        EmbedRequest embedRequest = new EmbedRequest(message);
        return QueryRequest.builder()
                .vector(Objects.requireNonNull(embeddingClient.embed(embedRequest).block())
                        .getData().getFirst().getEmbedding())
                .topK(topK)
                .namespace(DEFAULT_NAMESPACE)
                .includeMetadata(true)  // Chatservice를 정의할 때 metadata에 접근해야 하므로 true로 설정
                .build();
    }

    public FetchRequest toFetchRequest(String id) {
        List<String> ids = new ArrayList<>(1);
        ids.add(id);

        return FetchRequest.builder()
                .ids(ids)
                .namespace(DEFAULT_NAMESPACE)
                .build();
    }

    private void addEmbeddingValues(Document document, List<Embedding> embeddings) {
        Mono<EmbedResponse> embedResponseMono = embeddingClient.embed(document);
        Embedding embedding = Embedding.builder()
                .id(document.getId())
                .values(Objects.requireNonNull(embedResponseMono.block()).getData().getFirst().getEmbedding())
                .metadata(document.getMetadata())
                .build();
        embeddings.add(embedding);
    }
}
