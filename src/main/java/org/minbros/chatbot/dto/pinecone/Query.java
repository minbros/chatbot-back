package org.minbros.chatbot.dto.pinecone;

import lombok.*;

import java.util.List;
import java.util.Map;

@Getter
@Builder
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class Query {
    private Map<String, Map<String, String>> filter;

    private String namespace;

    private SparseValue sparseValues;

    private int topK;

    @NonNull
    private List<Double> values;
}
