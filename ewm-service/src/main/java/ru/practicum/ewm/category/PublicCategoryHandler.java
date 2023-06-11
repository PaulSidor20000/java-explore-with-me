package ru.practicum.ewm.category;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import ru.practicum.ewm.exceptions.DataNotFoundException;
import ru.practicum.ewm.exceptions.ErrorHandler;

import static java.lang.String.format;

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
        int id = Integer.parseInt(request.pathVariables().get("id"));

        return repository.findById(id)
                .map(mapper::map)
                .flatMap(dto ->
                        ServerResponse
                                .status(HttpStatus.OK)
                                .bodyValue(dto))
                .switchIfEmpty(Mono.error(new DataNotFoundException(format("Category with id=%d was not found", id))))
                .onErrorResume(ErrorHandler::handler);
    }

    private Pageable getRequestPages(ServerRequest request) {
        int from = request.queryParam("from").map(Integer::parseInt).orElse(0);
        int size = request.queryParam("size").map(Integer::parseInt).orElse(10);

        return PageRequest.of(from, size);
    }

}
