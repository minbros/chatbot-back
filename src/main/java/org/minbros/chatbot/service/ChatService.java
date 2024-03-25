package org.minbros.chatbot.service;

import lombok.RequiredArgsConstructor;
import org.minbros.chatbot.dto.ResponseGenerateDto;
import org.minbros.chatbot.entity.Chat;
import org.minbros.chatbot.repository.ChatRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatClient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final Logger logger = LoggerFactory.getLogger(ChatService.class);
    private final OpenAiChatClient chatClient;
    private final ChatRepository chatRepository;

    public Map<String, String> generate(ResponseGenerateDto generateDto) {
        String response = chatClient.call(generateDto.getMessage());
        logger.info("GPT 응답: {}", response);
        chatRepository.save(new Chat(generateDto.getMessage(), response));
        return Map.of("generation", response);
    }

    public Flux<ChatResponse> generateStream(ResponseGenerateDto generateDto) {
        Prompt prompt = new Prompt(new UserMessage(generateDto.getMessage()));
        return chatClient.stream(prompt);
    }
}
