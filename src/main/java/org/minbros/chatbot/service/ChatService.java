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
import org.minbros.chatbot.util.Promptgenerator;
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
    private final ChatRepository chatRepository;
    private final OpenAiChatClient chatClient;
    private final PineconeClient pineconeClient;
    private final PineconeRequestGenerator pineconeRequestGenerator;
    private final Promptgenerator promptgenerator;
    private static final double THRESHOLD = 0.8;
    private static final String UNEXPECTED = "다음과 같이 출력해줘: " +
            "죄송해요, 저는 제가 알고 있는 질문만 대답해드릴 수 있어요! 만약 제 답변이 " +
            "만족스럽지 않으시다면, 좀 더 구체적으로 질문하거나 언제든지 저희에게 알려주세요!";

    public Map<String, String> generate(ChatRequest request) {
        Result result = getResult(request);
        String response;

        if (result.score() < THRESHOLD) {
            // 쿼리된 데이터와 별로 유사하지 않으면 그냥 그대로 gpt 응답을 보냄
            response = chatClient.call(UNEXPECTED);
        }
        else {
            // 쿼리해서 score가 임계값 이상을 충족시켰을 경우, metadata에서
            // 추출한 metadata에 따라서 http 요청을 통해 데이터를 받아오는 메소드를 수행
            // ex) keyword가 "공지사항"일 경우, 시립대 홈페이지의 공지사항을 긁어옴
            // 성능을 위해 사용해야 할 metadata는 keyword 말고도 추가적으로 더 들어가야 할 수 있음
            String prompt = promptgenerator.generateProperPrompt(result.metadata(), request);
            // http 요청을 통해 받아온 데이터를 gpt에 적절하게 프롬프팅해서
            // 원하는 결과를 만들어내도록 해야함
            response = chatClient.call(prompt);
        }
        chatRepository.save(new Chat(request.getMessage(), response));
        return Map.of("generation", response);
    }

    public Flux<ChatResponse> generateStream(ChatRequest request) {
        Result result = getResult(request);
        Prompt prompt;

        if (result.score() < THRESHOLD) {
            prompt = new Prompt(UNEXPECTED);
        } else {
            prompt = new Prompt(promptgenerator.generateProperPrompt(result.metadata(), request));
        }
        return chatClient.stream(prompt);
    }

    private Result getResult(ChatRequest request) {
        QueryRequest queryRequest = pineconeRequestGenerator.toQueryRequest(request.getMessage(), 1);
        QueryResponse queryResponse = pineconeClient.query(queryRequest).block();

        double score = Objects.requireNonNull(queryResponse).getMatches().getFirst().getScore();
        Map<String, Object> metadata = Objects.requireNonNull(queryResponse).getMatches().getFirst().getMetadata();
        log.info("쿼리된 데이터의 content: {}", metadata.get("content"));
        log.info("쿼리된 데이터의 score: {}", score);

        return new Result(score, metadata);
    }

    private record Result(double score, Map<String, Object> metadata) {
    }
}
