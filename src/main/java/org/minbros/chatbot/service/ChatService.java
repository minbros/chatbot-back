package org.minbros.chatbot.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.minbros.chatbot.dto.openai.GenerateRequest;
import org.minbros.chatbot.entity.Chat;
import org.minbros.chatbot.repository.ChatRepository;
import org.minbros.chatbot.client.PineconeClient;
import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatClient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatService {
    private final OpenAiChatClient chatClient;
    private final ChatRepository chatRepository;
    private final PineconeClient pineconeClient;

    public Map<String, String> generate(GenerateRequest request) {
        String response = chatClient.call(request.getMessage());
        log.info("GPT 응답: {}", response);
        chatRepository.save(new Chat(request.getMessage(), response));
        return Map.of("generation", response);
    }

    public Flux<ChatResponse> generateStream(GenerateRequest request) {
        Prompt prompt = new Prompt(new UserMessage(request.getMessage()));
        return chatClient.stream(prompt);
    }
}
