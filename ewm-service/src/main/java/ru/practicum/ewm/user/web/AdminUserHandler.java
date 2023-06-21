package ru.practicum.ewm.user.web;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import ru.practicum.ewm.exceptions.CategoryNotFoundException;
import ru.practicum.ewm.exceptions.ErrorHandler;
import ru.practicum.ewm.user.dto.NewUserRequest;
import ru.practicum.ewm.user.dto.UserMapper;
import ru.practicum.ewm.user.repository.UserRepository;
import ru.practicum.ewm.validators.DtoValidator;

@Service
@RequiredArgsConstructor
public class AdminUserHandler {
    private final UserRepository repository;
    private final UserMapper mapper;
    private final DtoValidator validator;

    public Mono<ServerResponse> findUser(ServerRequest request) {
        return repository.findAllBy(getRequestPages(request))
                .map(mapper::map)
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
                .map(mapper::map)
                .flatMap(repository::save)
                .map(mapper::map)
                .flatMap(dto ->
                        ServerResponse
                                .status(HttpStatus.CREATED)
                                .bodyValue(dto))
                .onErrorResume(ErrorHandler::handler);
    }

    public Mono<ServerResponse> deleteUser(ServerRequest request) {
        int categoryId = Integer.parseInt(request.pathVariable("id"));

        return repository.deleteById(categoryId)
                .then(ServerResponse
                        .status(HttpStatus.NO_CONTENT)
                        .body(Mono.just("Пользоваль удалён"), String.class))
                .switchIfEmpty(Mono.error(new CategoryNotFoundException(categoryId)))
                .onErrorResume(ErrorHandler::handler);
    }

    private Pageable getRequestPages(ServerRequest request) {
        int from = request.queryParam("from").map(Integer::parseInt).orElse(0);
        int size = request.queryParam("size").map(Integer::parseInt).orElse(10);

        return PageRequest.of(from, size);
    }

}
