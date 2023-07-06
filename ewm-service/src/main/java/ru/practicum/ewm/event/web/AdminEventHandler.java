package ru.practicum.ewm.event.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import ru.practicum.ewm.event.dto.UpdateEventAdminRequest;
import ru.practicum.ewm.event.service.AdminEventService;
import ru.practicum.ewm.exceptions.ErrorHandler;
import ru.practicum.ewm.validators.DtoValidator;
import ru.practicum.ewm.validators.EventValidator;

@Component
@RequiredArgsConstructor
public class AdminEventHandler {
    private final DtoValidator validator;
    private final AdminEventService service;
    private static final String EVENT_ID = "eventId";

    public Mono<ServerResponse> findEvent(ServerRequest request) {
        return Mono.just(request)
                .map(ServerRequest::queryParams)
//                .doOnNext(EventValidator::validateAdminParams)
                .doOnNext(EventValidator::validatePublicParams)
                .flatMapMany(service::findEvents)
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
                .doOnNext(dto -> {
                    if (dto.getAnnotation() != null && dto.getDescription() != null && dto.getTitle() != null) {
                        validator.validate(dto);
                    }
                })
                .flatMap(dto -> service.updateEvent(eventId, dto))
                .flatMap(dto ->
                        ServerResponse
                                .status(HttpStatus.OK)
                                .bodyValue(dto))
                .onErrorResume(ErrorHandler::handler);
    }
}
