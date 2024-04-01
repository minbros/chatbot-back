package org.minbros.chatbot.dto.openai;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class EmbedResponse {
    private String object;

    private List<EmbeddingData> data;

    private String model;

    private Map<String, Integer> usage;

    @Data
    public static class EmbeddingData {
        private String object;

        private List<Double> embedding;

        private Integer index;
    }
}
