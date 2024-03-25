package org.minbros.chatbot.config;

import org.springframework.ai.embedding.EmbeddingClient;
import org.springframework.ai.vectorstore.PineconeVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PineconeConfig {
    @Value("${spring.ai.vectorstore.pinecone.api-key}")
    private String pineconeAPIKey;

    @Bean
    public PineconeVectorStore.PineconeVectorStoreConfig pineconeVectorStoreConfig() {

        return PineconeVectorStore.PineconeVectorStoreConfig.builder().withApiKey(pineconeAPIKey)
                .withEnvironment("apw5-4e34-81fa")
                .withProjectId("7y2rpah")
                .withIndexName("sidae-bot-db")
                .withNamespace("uos")
                .build();
    }

    @Bean
    public VectorStore vectorStore(PineconeVectorStore.PineconeVectorStoreConfig config, EmbeddingClient embeddingClient) {
        return new PineconeVectorStore(config, embeddingClient);
    }
}
