package ru.practicum.ewm.event.web;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import ru.practicum.ewm.event.repository.EventRepository;

@Service
@RequiredArgsConstructor
public class PublicEventHandler {
    private final EventRepository repository;

    public Mono<ServerResponse> findEvents(ServerRequest request) {
        return null;
    }

    public Mono<ServerResponse> findEventById(ServerRequest request) {
        return null;
    }
}
