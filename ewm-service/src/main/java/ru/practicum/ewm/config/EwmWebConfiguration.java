package ru.practicum.ewm.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import ru.practicum.statclient.client.StatClient;
import ru.practicum.statclient.config.ClientConfiguration;

@Configuration
@RequiredArgsConstructor
@Import(ClientConfiguration.class)
public class EwmWebConfiguration {
    private final ObjectMapper mapper;
    private final ClientConfiguration configuration;

    @Bean
    public StatClient statClient() {
        return StatClient.builder()
                .client(configuration.webClientConfig())
                .mapper(mapper)
                .build();
    }
}
