package org.minbros.chatbot.service;

import org.minbros.chatbot.entity.Chat;
import org.minbros.chatbot.repository.ChatRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatClient;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.Map;

@Service
public class ChatService {
    private static final Logger logger = LoggerFactory.getLogger(ChatService.class);
    private final OpenAiChatClient chatClient;
    private final ChatRepository chatRepository;
    private final VectorStore vectorStore;

    @Autowired
    public ChatService(OpenAiChatClient chatClient, ChatRepository chatRepository, VectorStore vectorStore) {
        this.chatClient = chatClient;
        this.chatRepository = chatRepository;
        this.vectorStore = vectorStore;
    }

    public Map<String, String> generate(String message) {
        String queriedText = vectorStore.similaritySearch(message).getFirst().getContent();
        logger.info("쿼리된 텍스트: {}", queriedText);

        // 성능 향상을 위해 request 내용 수정 필요
        String request = String.format("\"%s\"에 관한 설명을 덧붙여줘", queriedText);
        logger.info("프롬프트: {}", request);

        String response = chatClient.call(request);
        logger.info("GPT 응답: {}", response);
        chatRepository.save(new Chat(message, response));
        return Map.of("generation", response);
    }

    // 나중에 generate 함수가 구현이 완료되면 구현할 에정
    public Flux<ChatResponse> generateStream(String message) {
        Prompt prompt = new Prompt(new UserMessage(message));
        return chatClient.stream(prompt);
    }
}
