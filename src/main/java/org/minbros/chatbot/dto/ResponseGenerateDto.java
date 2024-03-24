package org.minbros.chatbot.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
public class ResponseGenerateDto {
    private String message;

    public ResponseGenerateDto(String message) {
        this.message = message;
    }
}
