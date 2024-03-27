package org.minbros.chatbot.dto.openai;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmbedRequest {
    private String input;
    private String model = "text-embedding-ada-002";

    public EmbedRequest(String input) {
        this.input = input;
    }
}
