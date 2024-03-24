package org.minbros.chatbot.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.minbros.chatbot.service.ChatService;
import org.springframework.ai.chat.ChatResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.Map;


@RequestMapping("/chat")
@RestController
@Tag(name = "챗봇 응답", description = "챗봇 응답 API")
public class ChatController {
    private final ChatService chatService;

    @Autowired
    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping("/ai/generate")
    public Map<String, String> generate(@RequestParam(value = "message") String message) {
        return chatService.generate(message);
    }

    @GetMapping("/ai/generateStream")
    public Flux<ChatResponse> generateStream(@RequestParam(value = "message") String message) {
        return chatService.generateStream(message);
    }
}