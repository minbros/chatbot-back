package org.minbros.chatbot.dto.pinecone;

import lombok.*;

import java.util.List;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class UpsertRequest {
    @NonNull
    private List<Embedding> vectors;

    private String namespace;
}
