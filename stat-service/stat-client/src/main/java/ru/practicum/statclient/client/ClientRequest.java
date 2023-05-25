package ru.practicum.statclient.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientRequest {
    private final WebClient client;
    private final ObjectMapper objectMapper;

    public <T> Mono<String> post(T dto, String uri) throws JsonProcessingException {
        String json = objectMapper.writeValueAsString(dto);

        return client.post()
                .uri(uri)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(json))
                .retrieve()
                .bodyToMono(String.class);
    }

    public Flux<String> get(String start, String end, Optional<Collection<String>> uris, Boolean unique, String uri) {
        return client.get()
                .uri(uriBuilder ->
                        uriBuilder
                                .path(uri)
                                .queryParam("start", start)
                                .queryParam("end", end)
                                .queryParamIfPresent("uris", uris)
                                .queryParam("unique", unique)
                                .build())
                .retrieve()
                .bodyToFlux(String.class);
    }

}
