package ru.practicum.ewm.event.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import ru.practicum.ewm.event.dto.EventMapper;
import ru.practicum.ewm.event.dto.UpdateEventAdminRequest;
import ru.practicum.ewm.event.service.AdminEventService;
import ru.practicum.ewm.exceptions.ErrorHandler;
import ru.practicum.ewm.validators.DtoValidator;

@Component
@RequiredArgsConstructor
public class AdminEventHandler {
    private final AdminEventService service;
    private final EventMapper mapper;
    private final DtoValidator validator;
    private static final String USER_ID = "userId";
    private static final String EVENT_ID = "eventId";

    public Mono<ServerResponse> findEvent(ServerRequest request) {
        MultiValueMap<String, String> params = request.queryParams();

        return service.findEvent(params)
                .collectList()
                .flatMap(dto ->
                        ServerResponse
                                .status(HttpStatus.OK)
                                .bodyValue(dto))
                .onErrorResume(ErrorHandler::handler);
    }

    public Mono<ServerResponse> updateEvent(ServerRequest request) {
        int eventId = Integer.parseInt(request.pathVariable(EVENT_ID));

        return request.bodyToMono(UpdateEventAdminRequest.class)
                .flatMap(dto -> service.updateEvent(eventId, dto))
                .flatMap(dto ->
                        ServerResponse
                                .status(HttpStatus.OK)
                                .bodyValue(dto))
                .onErrorResume(ErrorHandler::handler);
    }
}
