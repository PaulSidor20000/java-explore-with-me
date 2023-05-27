package ru.practicum.statclient.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;

public interface StatClient {
    <T> Mono<String> post(T dto, String uri) throws JsonProcessingException;

    Flux<String> get(String start, String end, Collection<String> uris, Boolean unique, String uri);

    static StatClient.Builder builder() {
        return new StatClientBuilder();
    }

    interface Builder {

        Builder client(WebClient client);

        Builder mapper(ObjectMapper mapper);

        StatClient build();
    }
}
