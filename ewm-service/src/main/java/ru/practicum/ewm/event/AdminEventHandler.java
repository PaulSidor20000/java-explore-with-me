package ru.practicum.ewm.event;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class AdminEventHandler {
    private final EventRepository repository;

    public Mono<ServerResponse> createEvent(ServerRequest request) {
        return null;
    }

    public Mono<ServerResponse> patchEvent(ServerRequest request) {
        return null;
    }
}
