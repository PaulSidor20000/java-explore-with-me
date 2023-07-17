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
    private String statServerUrl;

    @Value("${geo.server.url}")
    private String geoServerUrl;

    @Bean
    public StatClient statClient() {
        return StatClient.builder()
                .client(getWebClient(statServerUrl))
                .mapper(mapper)
                .build();
    }

    @Bean
    public GeoClient getGeoClient() {
        return GeoClient.builder()
                .client(getWebClient(geoServerUrl))
                .mapper(mapper)
                .build();
    }

    private WebClient getWebClient(String baseUrl) {
        return WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

}
