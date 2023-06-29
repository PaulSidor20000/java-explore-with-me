package ru.practicum.ewm.event.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import ru.practicum.ewm.event.service.PublicEventService;
import ru.practicum.ewm.exceptions.ErrorHandler;

@Service
@RequiredArgsConstructor
public class PublicEventHandler {
    private final PublicEventService service;
    private static final String EVENT_ID = "eventId";

    public Mono<ServerResponse> findEvents(ServerRequest request) {
        MultiValueMap<String, String> params = request.queryParams();

        return service.findEvents(params)
                .collectList()
                .flatMap(dto ->
                        ServerResponse
                                .status(HttpStatus.OK)
                                .bodyValue(dto))
                .onErrorResume(ErrorHandler::handler);
    }

    public Mono<ServerResponse> findEventById(ServerRequest request) {
        int eventId = Integer.parseInt(request.pathVariable(EVENT_ID));

        return service.findEventById(eventId)
                .flatMap(dto ->
                        ServerResponse
                                .status(HttpStatus.OK)
                                .bodyValue(dto))
                .onErrorResume(ErrorHandler::handler);
    }
}
