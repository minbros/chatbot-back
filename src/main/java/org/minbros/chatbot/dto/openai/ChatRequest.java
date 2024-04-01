package org.minbros.chatbot.dto.openai;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatRequest {
    @NonNull
    private String message;
}
