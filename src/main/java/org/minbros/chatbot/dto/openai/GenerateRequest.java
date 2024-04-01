package org.minbros.chatbot.dto.openai;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GenerateRequest {
    @NonNull
    private String message;
}
