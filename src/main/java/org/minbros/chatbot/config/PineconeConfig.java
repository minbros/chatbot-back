//package org.minbros.chatbot.config;
//
//import org.springframework.ai.embedding.EmbeddingClient;
//import org.springframework.ai.vectorstore.PineconeVectorStore;
//import org.springframework.ai.vectorstore.VectorStore;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class PineconeConfig {
//    @Bean
//    public PineconeVectorStore.PineconeVectorStoreConfig pineconeVectorStoreConfig() {
//
//        return PineconeVectorStore.PineconeVectorStoreConfig.builder().build();
//    }
//
//    @Bean
//    public VectorStore vectorStore(PineconeVectorStore.PineconeVectorStoreConfig config, EmbeddingClient embeddingClient) {
//        return new PineconeVectorStore(config, embeddingClient);
//    }
//}
