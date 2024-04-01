package org.minbros.chatbot.dto.pinecone;

import lombok.*;

import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class Vector {
    @NonNull
    private String id;

    @NonNull
    private List<Double> values;

    private SparseValue sparseValues;

    private Map<String, Object> metadata;
}
