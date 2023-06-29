package ru.practicum.ewm.request.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import ru.practicum.ewm.exceptions.ErrorHandler;
import ru.practicum.ewm.exceptions.RequestConditionException;
import ru.practicum.ewm.request.service.PrivateRequestService;

@Component
@RequiredArgsConstructor
public class PrivateRequestHandler {
    private final PrivateRequestService service;
    private static final String USER_ID = "userId";
    private static final String EVENT_ID = "eventId";
    private static final String REQUEST_ID = "requestId";

    public Mono<ServerResponse> createNewRequest(ServerRequest request) {
        int userId = Integer.parseInt(request.pathVariable((USER_ID)));
        int eventId = Integer.parseInt(request.queryParam(EVENT_ID).orElse("-1"));

        return service.createNewRequest(userId, eventId)
                .flatMap(dto ->
                        ServerResponse
                                .status(HttpStatus.CREATED)
                                .bodyValue(dto))
                .onErrorResume(ErrorHandler::handler);
    }

    public Mono<ServerResponse> findUserRequests(ServerRequest request) {
        int userId = Integer.parseInt(request.pathVariable(USER_ID));

        return service.findUserRequests(userId)
                .collectList()
                .flatMap(dto ->
                        ServerResponse
                                .status(HttpStatus.OK)
                                .bodyValue(dto))
                .onErrorResume(ErrorHandler::handler);
    }

    public Mono<ServerResponse> cancelUserRequest(ServerRequest request) {
        int userId = Integer.parseInt(request.pathVariable((USER_ID)));
        int requestId = Integer.parseInt(request.pathVariable((REQUEST_ID)));

        return service.cancelUserRequest(userId, requestId)
                .flatMap(dto ->
                        ServerResponse
                                .status(HttpStatus.OK)
                                .bodyValue(dto))
                .onErrorResume(ErrorHandler::handler);
    }

}
