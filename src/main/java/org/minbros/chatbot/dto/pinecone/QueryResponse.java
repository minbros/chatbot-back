package org.minbros.chatbot.dto.pinecone;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class QueryResponse {
    private List<Object> results;

    private List<Match> matches;

    private String namespace;

    private Map<String, Integer> usage;

    @Data
    public static class Match {
        private String id;

        private Double score;

        private List<Double> values;
    }
}
