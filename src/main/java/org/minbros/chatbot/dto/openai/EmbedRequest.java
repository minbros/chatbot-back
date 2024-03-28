package org.minbros.chatbot.dto.openai;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class EmbedRequest {
    @NonNull
    private String input;

    @Builder.Default
    private String model = "text-embedding-ada-002";
}
