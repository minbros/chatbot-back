package org.minbros.chatbot.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.minbros.chatbot.dto.openai.ChatRequest;
import org.minbros.chatbot.dto.pinecone.QueryRequest;
import org.minbros.chatbot.dto.pinecone.QueryResponse;
import org.minbros.chatbot.entity.Chat;
import org.minbros.chatbot.repository.ChatRepository;
import org.minbros.chatbot.client.PineconeClient;
import org.minbros.chatbot.util.PineconeRequestGenerator;
import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatClient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatService {
    private final OpenAiChatClient chatClient;
    private final ChatRepository chatRepository;
    private final PineconeClient pineconeClient;
    private final PineconeRequestGenerator pineconeRequestGenerator;
    private static final double THRESHOLD = 0.75;

    public Map<String, String> generate(ChatRequest request) {
        String response = chatClient.call(request.getMessage());
        log.info("GPT 응답: {}", response);
        chatRepository.save(new Chat(request.getMessage(), response));
        return Map.of("generation", response);
    }

    @SuppressWarnings("BlockingMethodInNonBlockingContext")
    public Flux<ChatResponse> generateStream(ChatRequest request) {
        QueryRequest queryRequest = pineconeRequestGenerator.toQueryRequest(request.getMessage(), 1);
        QueryResponse queryResponse = pineconeClient.query(queryRequest).block();

        double score = Objects.requireNonNull(queryResponse).getMatches().getFirst().getScore();
        Map<String, Object> metadata = Objects.requireNonNull(queryResponse).getMatches().getFirst().getMetadata();
        log.info("쿼리된 데이터의 content: {}", metadata.get("content"));

        if (score < THRESHOLD) {
            // 쿼리된 데이터와 별로 유사하지 않으면 그냥 그대로 gpt 응답을 보냄
            Prompt prompt = new Prompt(request.getMessage());
            return chatClient.stream(prompt);
        }
        else {
            // 쿼리해서 score가 임계값 이상을 충족시켰을 경우, metadata에서
            // 추출한 metadata에 따라서 http 요청을 통해 데이터를 받아오는 메소드를 수행
            // ex) keyword가 "공지사항"일 경우, 시립대 홈페이지의 공지사항을 긁어옴
            // 성능을 위해 사용해야 할 metadata는 keyword 말고도 추가적으로 더 들어가야 할 수 있음
            requestSomeAPI(metadata);
            // http 요청을 통해 받아온 데이터를 gpt에 적절하게 프롬프팅해서
            // 원하는 결과를 만들어내도록 해야함
            return null;
        }
    }

    // metadata별 api 요청을 위해 구현해야 하는 메소드
    private String requestSomeAPI(Map<String, Object> metadata) {
        return metadata.get("keyword").toString();
    }
}
