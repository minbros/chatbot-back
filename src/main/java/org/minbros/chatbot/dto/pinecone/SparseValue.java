package org.minbros.chatbot.dto.pinecone;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
public class SparseValue {
    @NonNull
    private List<Integer> indices;

    @NonNull
    private List<Double> values;
}
