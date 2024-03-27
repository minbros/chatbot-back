package org.minbros.chatbot.dto.pinecone;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class SparseValue {
    @NonNull
    private List<Integer> indices;

    @NonNull
    private List<Double> values;
}
