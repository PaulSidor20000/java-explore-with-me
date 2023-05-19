package ru.practicum.statclient.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import ru.practicum.statclient.dto.RequestDto;

import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class RequestClient {
    private final ObjectMapper objectMapper;
    WebClient client = WebClient.builder()
            .baseUrl("${stat-server.url}")
            .build();

    public ResponseEntity<String> post(RequestDto dto, String uri) throws JsonProcessingException {
        String json = objectMapper.writeValueAsString(dto);

        return client.post()
                .uri(uri)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(json))
                .retrieve()
                .toEntity(String.class)
                .block();
    }

    public ResponseEntity<String> get(String start, String end, String[] uris, Boolean unique, String uri) {
        return client.get()
                .uri(uriBuilder ->
                        uriBuilder
                                .queryParam("start", start)
                                .queryParam("end", end)
                                .queryParam("uris", Arrays.asList(uris))
                                .queryParam("unique", unique)
                                .build(uri))
                .retrieve()
                .toEntity(String.class)
                .block();
    }
}
