package ru.practicum.statclient.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ClientConfiguration {

    @Value("${stat.server.url}")
    private String serverUrl;

    @Bean
    public WebClient webClientConfig() {
        return WebClient.builder()
                .baseUrl(serverUrl)
                .build();
    }
}
