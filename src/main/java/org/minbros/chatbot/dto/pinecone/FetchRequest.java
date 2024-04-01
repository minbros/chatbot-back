package org.minbros.chatbot.dto.pinecone;

import lombok.*;

import java.util.List;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class FetchRequest {
    @NonNull
    private List<String> ids;

    private String namespace;
}
