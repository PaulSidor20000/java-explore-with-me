package ru.practicum.ewm.category.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import ru.practicum.ewm.category.service.PublicCategoryService;
import ru.practicum.ewm.exceptions.CategoryNotFoundException;
import ru.practicum.ewm.exceptions.ErrorHandler;

@Component
@RequiredArgsConstructor
public class PublicCategoryHandler {
    private final PublicCategoryService categoryService;

    public Mono<ServerResponse> findCategories(ServerRequest request) {
        MultiValueMap<String, String> params = request.queryParams();

        return categoryService.findCategories(params)
                .collectList()
                .flatMap(dto ->
                        ServerResponse
                                .status(HttpStatus.OK)
                                .bodyValue(dto))
                .onErrorResume(ErrorHandler::handler);
    }

    public Mono<ServerResponse> findCategoryById(ServerRequest request) {
        int categoryId = Integer.parseInt(request.pathVariables().get("id"));

        return categoryService.findCategoryById(categoryId)
                .flatMap(dto ->
                        ServerResponse
                                .status(HttpStatus.OK)
                                .bodyValue(dto))
                .switchIfEmpty(Mono.error(new CategoryNotFoundException(categoryId)))
                .onErrorResume(ErrorHandler::handler);
    }

}
