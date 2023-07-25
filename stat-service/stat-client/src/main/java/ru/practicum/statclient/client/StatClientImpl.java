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

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Optional;

@RequiredArgsConstructor
class StatClientImpl implements StatClient {
    private final WebClient client;
    private final ObjectMapper objectMapper;
    private static final String SERVER_URI = "/stats";
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public <T> Mono<String> post(T dto, String serverUri) throws JsonProcessingException {
        String json = objectMapper.writeValueAsString(dto);

        return client.post()
                .uri(serverUri)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(json))
                .retrieve()
                .bodyToMono(String.class);
    }

    @Override
    public Flux<String> get(Collection<String> uris) {
        return get(
                LocalDateTime.now().minusYears(100).format(formatter),
                LocalDateTime.now().plusYears(100).format(formatter),
                uris, false, SERVER_URI);
    }

    public Flux<String> get(Collection<String> uris, String serverUri) {
        return get(
                LocalDateTime.now().minusYears(100).format(formatter),
                LocalDateTime.now().plusYears(100).format(formatter),
                uris, false, serverUri);
    }

    @Override
    public Flux<String> get(Collection<String> uris, Boolean unique) {
        return get(
                LocalDateTime.now().minusYears(100).format(formatter),
                LocalDateTime.now().plusYears(100).format(formatter),
                uris, unique, SERVER_URI);
    }

    public Flux<String> get(Collection<String> uris, Boolean unique, String serverUri) {
        return get(
                LocalDateTime.now().minusYears(100).format(formatter),
                LocalDateTime.now().plusYears(100).format(formatter),
                uris, unique, serverUri);
    }

    @Override
    public Flux<String> get(String start, String end, Collection<String> uris, Boolean unique, String uri) {
        return client.get()
                .uri(uriBuilder ->
                        uriBuilder
                                .path(uri)
                                .queryParam("start", start)
                                .queryParam("end", end)
                                .queryParamIfPresent("uris", Optional.ofNullable(uris))
                                .queryParam("unique", unique)
                                .build())
                .retrieve()
                .bodyToFlux(String.class);
    }

}
