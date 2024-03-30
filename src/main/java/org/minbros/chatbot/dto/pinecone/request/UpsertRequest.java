package org.minbros.chatbot.dto.pinecone.request;

import lombok.*;
import org.minbros.chatbot.dto.pinecone.SparseValue;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class UpsertRequest {
    @NonNull
    private List<Vector> vectors;

    private String namespace;

    @Getter
    @Builder
    @NoArgsConstructor
    @RequiredArgsConstructor
    @AllArgsConstructor
    public static class Vector {
        @Builder.Default
        private String id = UUID.randomUUID().toString();

        @NonNull
        private List<Double> values;

        private SparseValue sparseValues;

        private Map<String, String> metadata;
    }
}
