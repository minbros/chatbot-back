package org.minbros.chatbot.dto.pinecone;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;

@Getter
@Builder
@RequiredArgsConstructor
public class Query {
    private Map<String, Map<String, String>> filter;

    private String namespace;

    private SparseValue sparseValues;

    private int topK;

    @NonNull
    private List<Double> values;
}
