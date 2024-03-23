package org.minbros.chatbot.service;

import org.minbros.chatbot.repository.ChatRepository;
import org.minbros.chatbot.repository.MemberRepository;
import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.Map;

@Service
public class ChatService {
    private final OpenAiChatClient chatClient;
    private final ChatRepository chatRepository;

    private final MemberRepository memberRepository;

    @Autowired
    public ChatService(OpenAiChatClient chatClient, ChatRepository chatRepository, MemberRepository memberRepository) {
        this.chatClient = chatClient;
        this.chatRepository = chatRepository;
        this.memberRepository = memberRepository;
    }

    public Map<String, String> generate(String message) {
        String response = chatClient.call(message);
        return Map.of("generation", response);
    }

    public Flux<ChatResponse> generateStream(String message) {
        Prompt prompt = new Prompt(new UserMessage(message));
        return chatClient.stream(prompt);
    }
}
