package com.sesametrees.ds.server;

import com.sesametrees.ds.dao.Details;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class StabilityClient {

    private final WebClient client;

    // Spring Boot auto-configures a `WebClient.Builder` instance with nice defaults and customizations.
    // We can use it to create a dedicated `WebClient` for our component.
    public StabilityClient(WebClient.Builder builder) {
        this.client = builder.baseUrl("http://localhost:8000")
                .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(3 * 1024 * 1024)) // 1 MB
                .build();
    }

    public Mono<String> getMessageUltra(String prompt) {
        return this.client.get().uri("/stabilityUltra/{prompt}", prompt).accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Details.class)
                .map(Details::getMessage);
    }
}
