package org.minbros.chatbot.dto.openai;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GenerateRequest {
    @NonNull
    private String message;
}
