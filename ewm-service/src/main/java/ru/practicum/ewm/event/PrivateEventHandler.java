package ru.practicum.ewm.event;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import ru.practicum.ewm.dto.NewEventDto;
import ru.practicum.ewm.exceptions.ErrorHandler;
import ru.practicum.ewm.validators.DtoValidator;

@Service
@RequiredArgsConstructor
public class PrivateEventHandler {
    private final EventRepository repository;
    private final EventMapper mapper;
    private final DtoValidator validator;

    public Mono<ServerResponse> findUserEvents(ServerRequest request) {
        return null;
    }

    public Mono<ServerResponse> createUserEvent(ServerRequest request) {
        int userId = Integer.parseInt(request.pathVariable("userId"));

        return request.bodyToMono(NewEventDto.class)
                .flatMap(validator::validate)
                .map(mapper::map)
                .flatMap(repository::save)
                .map(mapper::map)
                .flatMap(dto ->
                        ServerResponse
                                .status(HttpStatus.CREATED)
                                .bodyValue(dto))
                .onErrorResume(ErrorHandler::handler);
    }

    public Mono<ServerResponse> findUserEventById(ServerRequest request) {
        return null;
    }

    public Mono<ServerResponse> patchUserEventById(ServerRequest request) {
        return null;
    }

    public Mono<ServerResponse> findRequestsOfUserEvent(ServerRequest request) {
        return null;
    }

    public Mono<ServerResponse> patchRequestsOfUserEvent(ServerRequest request) {
        return null;
    }
}
