package org.minbros.chatbot.client;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class UOSClient {
    // Wise에 있는 openAPI를 불러오는 객체
    private final String uosAPIKey = System.getenv("UOS_API_KEY");
    private final WebClient webClient;

    public UOSClient() {
        this.webClient = WebClient.builder()
                .baseUrl("https://wise.uos.ac.kr/uosdoc")
                .build();
    }

    public Mono<String> getAnnouncement() {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api.ApiApiMainBd.oapi")
                        .queryParam("apiKey", uosAPIKey)
                        .build())
                .retrieve()
                .bodyToMono(String.class);
    }
}
