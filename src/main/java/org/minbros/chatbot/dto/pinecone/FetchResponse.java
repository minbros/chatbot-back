package org.minbros.chatbot.dto.pinecone;

import lombok.Data;

import java.util.Map;

@Data
public class FetchResponse {
    private Map<String, Vector> vectors;

    private String namespace;

    private Map<String, Integer> usage;
}
