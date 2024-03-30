package org.minbros.chatbot.dto.pinecone.request;

import lombok.*;
import org.minbros.chatbot.dto.pinecone.PineconeVector;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class UpsertRequest {
    @NonNull
    private List<PineconeVector> vectors;

    private String namespace;
}
