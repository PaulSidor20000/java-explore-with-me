package ru.practicum.ewm.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import ru.practicum.statclient.client.StatClient;

@Configuration
@RequiredArgsConstructor
public class EwmWebConfiguration {
    private final ObjectMapper mapper;

    @Value("${stat.server.url}")
    private String serverUrl;

    @Bean
    public StatClient statClient() {
        return StatClient.builder()
                .client(getWebClient())
                .mapper(mapper)
                .build();
    }

    private WebClient getWebClient() {
        return WebClient.builder()
                .baseUrl(serverUrl)
                .build();
    }

}
