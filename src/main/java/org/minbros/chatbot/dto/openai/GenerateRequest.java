package org.minbros.chatbot.dto.openai;

import lombok.*;

@Getter
@RequiredArgsConstructor
public class GenerateRequest {
    @NonNull
    private String message;
}
