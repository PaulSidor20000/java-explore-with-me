package ru.practicum.ewm.user.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import ru.practicum.ewm.exceptions.CategoryNotFoundException;
import ru.practicum.ewm.exceptions.ErrorHandler;
import ru.practicum.ewm.exceptions.UserNotFoundException;
import ru.practicum.ewm.user.dto.NewUserRequest;
import ru.practicum.ewm.user.service.AdminUserService;
import ru.practicum.ewm.validators.DtoValidator;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminUserHandler {
    private final AdminUserService userService;
    private final DtoValidator validator;

    public Mono<ServerResponse> findUsers(ServerRequest request) {
        List<Integer> ids = request.queryParam("ids").stream().map(Integer::parseInt).collect(Collectors.toList());
        int from = request.queryParam("from").map(Integer::parseInt).orElse(0);
        int size = request.queryParam("size").map(Integer::parseInt).orElse(10);

        return userService.findUsers(ids, from, size)
                .collectList()
                .flatMap(dto ->
                        ServerResponse
                                .status(HttpStatus.OK)
                                .bodyValue(dto))
                .onErrorResume(ErrorHandler::handler);
    }

    public Mono<ServerResponse> createUser(ServerRequest request) {
        return request.bodyToMono(NewUserRequest.class)
                .doOnNext(validator::validate)
                .flatMap(userService::createUser)
                .flatMap(dto ->
                        ServerResponse
                                .status(HttpStatus.CREATED)
                                .bodyValue(dto))
                .onErrorResume(ErrorHandler::handler);
    }

    public Mono<ServerResponse> deleteUser(ServerRequest request) {
        int userId = Integer.parseInt(request.pathVariable("id"));

        return userService.deleteUser(userId)
                .then(ServerResponse
                        .status(HttpStatus.NO_CONTENT)
                        .body(Mono.just("Пользоваль удалён"), String.class))
            //    .switchIfEmpty(Mono.error(new UserNotFoundException(userId)))
                .onErrorResume(ErrorHandler::handler);
    }

}
