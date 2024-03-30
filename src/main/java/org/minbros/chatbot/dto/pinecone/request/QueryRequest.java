package org.minbros.chatbot.dto.pinecone.request;

import lombok.*;
import org.minbros.chatbot.dto.pinecone.SparseValue;

import java.util.List;
import java.util.Map;

@Getter
@Builder
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
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

    @Getter
    @Builder
    @NoArgsConstructor
    @RequiredArgsConstructor
    @AllArgsConstructor
    public static class Query {
        private Map<String, Map<String, String>> filter;

        private String namespace;

        private SparseValue sparseValues;

        private int topK;

        @NonNull
        private List<Double> values;
    }
}
