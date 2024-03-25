package org.minbros.chatbot.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.minbros.chatbot.dto.ResponseGenerateDto;
import org.minbros.chatbot.service.ChatService;
import org.springframework.ai.chat.ChatResponse;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.Map;


@RequestMapping("/chat")
@RequiredArgsConstructor
@RestController
@Tag(name = "챗봇 응답", description = "챗봇 응답 API")
public class ChatController {
    private final ChatService chatService;

    @GetMapping("/generate")
    public Map<String, String> generate(@RequestBody ResponseGenerateDto generateDto) {
        return chatService.generate(generateDto);
    }

    @GetMapping("/generateStream")
    public Flux<ChatResponse> generateStream(ResponseGenerateDto generateDto) {
        return chatService.generateStream(generateDto.getMessage());
    }
}