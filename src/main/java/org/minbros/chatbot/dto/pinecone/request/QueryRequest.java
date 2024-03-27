package org.minbros.chatbot.dto.pinecone.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.minbros.chatbot.dto.pinecone.Query;
import org.minbros.chatbot.dto.pinecone.SparseValue;

import java.util.List;
import java.util.Map;

@Getter
@Builder
@RequiredArgsConstructor
public class QueryRequest {
    private Map<String, Map<String, String>> filter;

    private List<Double> vector;

    private String id;

    @Builder.Default
    private boolean includeMetadata = false;

    @Builder.Default
    private boolean includeValues = false;

    private String namespace;

    private List<Query> queries;

    private SparseValue sparseVector;

    @NonNull
    private Integer topK;
}
