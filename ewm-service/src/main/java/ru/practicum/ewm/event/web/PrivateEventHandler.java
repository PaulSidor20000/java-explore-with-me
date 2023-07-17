package ru.practicum.ewm.event.web;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import ru.practicum.ewm.event.dto.EventMapper;
import ru.practicum.ewm.event.dto.EventRequestStatusUpdateRequest;
import ru.practicum.ewm.event.dto.NewEventDto;
import ru.practicum.ewm.event.dto.UpdateEventUserRequest;
import ru.practicum.ewm.event.service.PrivateEventService;
import ru.practicum.ewm.exceptions.ErrorHandler;
import ru.practicum.ewm.locations.service.AdminLocationService;
import ru.practicum.ewm.utils.DtoValidator;
import ru.practicum.ewm.utils.EventValidator;

@Service
@RequiredArgsConstructor
public class PrivateEventHandler {
    private final PrivateEventService service;
    private final AdminLocationService locationService;
    private final EventMapper mapper;
    private final DtoValidator validator;
    private static final String USER_ID = "userId";
    private static final String EVENT_ID = "eventId";

    public Mono<ServerResponse> findUserEvents(ServerRequest request) {
        int userId = Integer.parseInt(request.pathVariable(USER_ID));

        return service.findUserEvents(userId, getRequestPages(request))
                .collectList()
                .flatMap(dto ->
                        ServerResponse
                                .status(HttpStatus.OK)
                                .bodyValue(dto))
                .onErrorResume(ErrorHandler::handler);
    }

    public Mono<ServerResponse> createNewEvent(ServerRequest request) {
        int userId = Integer.parseInt(request.pathVariable(USER_ID));

        return request.bodyToMono(NewEventDto.class)
                .doOnNext(validator::validate)
                .doOnNext(EventValidator::newEventValidator)
                .publishOn(Schedulers.boundedElastic())   // TODO publishOn
                .doOnNext(dto -> locationService.createLocation(dto.getLocation()).subscribe())
                .map(dto -> mapper.merge(userId, dto))
                .flatMap(service::createNewEvent)
                .flatMap(dto ->
                        ServerResponse
                                .status(HttpStatus.CREATED)
                                .bodyValue(dto))
                .onErrorResume(ErrorHandler::handler);
    }

    public Mono<ServerResponse> findUserEventById(ServerRequest request) {
        int eventId = Integer.parseInt(request.pathVariable(EVENT_ID));

        return service.findUserEventById(eventId)
                .flatMap(dto ->
                        ServerResponse
                                .status(HttpStatus.OK)
                                .bodyValue(dto))
                .onErrorResume(ErrorHandler::handler);
    }

    public Mono<ServerResponse> updateUserEventById(ServerRequest request) {
        int userId = Integer.parseInt(request.pathVariable(USER_ID));
        int eventId = Integer.parseInt(request.pathVariable(EVENT_ID));

        return request.bodyToMono(UpdateEventUserRequest.class)
                .doOnNext(validator::validate)
                .flatMap(dto -> service.updateUserEventById(userId, eventId, dto))
                .flatMap(dto ->
                        ServerResponse
                                .status(HttpStatus.OK)
                                .bodyValue(dto))
                .onErrorResume(ErrorHandler::handler);
    }

    public Mono<ServerResponse> findRequestsOfUserEvent(ServerRequest request) {
        int eventId = Integer.parseInt(request.pathVariable(EVENT_ID));

        return service.findRequestsOfUserEvent(eventId)
                .collectList()
                .flatMap(dto ->
                        ServerResponse
                                .status(HttpStatus.OK)
                                .bodyValue(dto))
                .onErrorResume(ErrorHandler::handler);
    }

    public Mono<ServerResponse> updateRequestsOfUserEvent(ServerRequest request) {
        int userId = Integer.parseInt(request.pathVariable(USER_ID));
        int eventId = Integer.parseInt(request.pathVariable(EVENT_ID));

        return request.bodyToMono(EventRequestStatusUpdateRequest.class)
                .flatMap(dto -> service.updateRequestsOfUserEvent(userId, eventId, dto))
                .flatMap(dto ->
                        ServerResponse
                                .status(HttpStatus.OK)
                                .bodyValue(dto))
                .onErrorResume(ErrorHandler::handler);
    }

    private Pageable getRequestPages(ServerRequest request) {
        int from = request.queryParam("from").map(Integer::parseInt).orElse(0);
        int size = request.queryParam("size").map(Integer::parseInt).orElse(10);

        return PageRequest.of(from, size);
    }

}
