package org.minbros.chatbot.dto.pinecone;

import lombok.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class PineconeVector {
    @Builder.Default
    private String id = UUID.randomUUID().toString();

    @NonNull
    private List<Double> values;

    private SparseValue sparseValues;

    private Map<String, String> metadata;
}

