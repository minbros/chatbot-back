package org.minbros.chatbot.dto.pinecone;

import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class UpsertRequest {
    @NonNull
    private List<Vector> vectors;

    private String namespace;
}
