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
import ru.practicum.ewm.locations.service.AdminLocationService;
import ru.practicum.ewm.utils.DtoValidator;
import ru.practicum.ewm.utils.ParamsValidator;

@Component
@RequiredArgsConstructor
public class AdminEventHandler {
//    private final DtoValidator validator;
//    private final AdminEventService service;
//    private final AdminLocationService locationService;
//    private static final String EVENT_ID = "eventId";

//    public Mono<ServerResponse> findEvents(ServerRequest request) {
//        return Mono.just(request.queryParams())
//                .doOnNext(ParamsValidator::validateParams)
//                .flatMapMany(service::findEvents)
//                .collectList()
//                .flatMap(dto ->
//                        ServerResponse
//                                .status(HttpStatus.OK)
//                                .bodyValue(dto))
//                .onErrorResume(ErrorHandler::handler);
//    }
//
//    public Mono<ServerResponse> updateEvent(ServerRequest request) {
//        int eventId = Integer.parseInt(request.pathVariable(EVENT_ID));
//
//        return request.bodyToMono(UpdateEventAdminRequest.class)
//                .doOnNext(dto -> {
//                    if (dto.getAnnotation() != null && dto.getDescription() != null && dto.getTitle() != null) {
//                        validator.validate(dto);
//                    }
//                })
//                .flatMap(updateEventAdminRequest -> {
//                    if (updateEventAdminRequest.getLocation() != null) {
//                        return locationService.createLocationFromEvent(updateEventAdminRequest);
//                    }
//                    return Mono.just(updateEventAdminRequest);
//                })
//                .flatMap(dto -> service.updateEvent(eventId, dto))
//                .flatMap(dto ->
//                        ServerResponse
//                                .status(HttpStatus.OK)
//                                .bodyValue(dto))
//                .onErrorResume(ErrorHandler::handler);
//    }

}
