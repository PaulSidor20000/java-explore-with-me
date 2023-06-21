package ru.practicum.ewm.category.web;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import ru.practicum.ewm.category.dto.CategoryMapper;
import ru.practicum.ewm.category.reposytory.CategoryRepository;
import ru.practicum.ewm.exceptions.CategoryNotFoundException;
import ru.practicum.ewm.exceptions.ErrorHandler;

@Component
@RequiredArgsConstructor
public class PublicCategoryHandler {
    private final CategoryRepository repository;
    private final CategoryMapper mapper;

    public Mono<ServerResponse> findCategories(ServerRequest request) {
        return repository.findAllBy(getRequestPages(request))
                .map(mapper::map)
                .collectList()
                .flatMap(dto ->
                        ServerResponse
                                .status(HttpStatus.OK)
                                .bodyValue(dto))
                .onErrorResume(ErrorHandler::handler);
    }

    public Mono<ServerResponse> findCategoryById(ServerRequest request) {
        int categoryId = Integer.parseInt(request.pathVariables().get("id"));

        return repository.findById(categoryId)
                .map(mapper::map)
                .flatMap(dto ->
                        ServerResponse
                                .status(HttpStatus.OK)
                                .bodyValue(dto))
                .switchIfEmpty(Mono.error(new CategoryNotFoundException(categoryId)))
                .onErrorResume(ErrorHandler::handler);
    }

    private Pageable getRequestPages(ServerRequest request) {
        int from = request.queryParam("from").map(Integer::parseInt).orElse(0);
        int size = request.queryParam("size").map(Integer::parseInt).orElse(10);

        return PageRequest.of(from, size);
    }

}
