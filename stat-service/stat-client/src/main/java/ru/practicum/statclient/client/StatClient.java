package ru.practicum.statclient.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;

public interface StatClient {
    <T> Mono<String> hit(T dto) throws JsonProcessingException;

    Flux<String> get(Collection<String> uris);

    Flux<String> get(Collection<String> uris, Boolean unique);

    Flux<String> get(String start, String end, Collection<String> uris, Boolean unique, String uri);

    static StatClient.Builder builder() {
        return new StatClientBuilder();
    }

    interface Builder {

        <T> Builder client(T client);

        <T> Builder mapper(T mapper);

        StatClient build();
    }
}
