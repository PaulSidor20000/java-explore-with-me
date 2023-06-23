package ru.practicum.ewm.category.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import ru.practicum.ewm.category.dto.NewCategoryDto;
import ru.practicum.ewm.category.service.AdminCategoryService;
import ru.practicum.ewm.exceptions.CategoryNotFoundException;
import ru.practicum.ewm.exceptions.ErrorHandler;
import ru.practicum.ewm.validators.DtoValidator;

@Service
@RequiredArgsConstructor
public class AdminCategoryHandler {
    private final AdminCategoryService categoryService;
    private final DtoValidator validator;

    public Mono<ServerResponse> createCategory(ServerRequest request) {
        return request.bodyToMono(NewCategoryDto.class)
                .doOnNext(validator::validate)
                .flatMap(categoryService::createCategory)
                .flatMap(dto ->
                        ServerResponse
                                .status(HttpStatus.CREATED)
                                .bodyValue(dto))
                .onErrorResume(ErrorHandler::handler);
    }

    public Mono<ServerResponse> deleteCategory(ServerRequest request) {
        int categoryId = Integer.parseInt(request.pathVariable("id"));

        return categoryService.deleteCategory(categoryId)
                .then(ServerResponse
                        .status(HttpStatus.NO_CONTENT)
                        .body(Mono.just("Категория удалена"), String.class))
                .switchIfEmpty(Mono.error(new CategoryNotFoundException(categoryId)))
                .onErrorResume(ErrorHandler::handler);
    }

    public Mono<ServerResponse> updateCategory(ServerRequest request) {
        int categoryId = Integer.parseInt(request.pathVariable("id"));

        return request.bodyToMono(NewCategoryDto.class)
                .doOnNext(validator::validate)
                .flatMap(dto -> categoryService.updateCategory(categoryId, dto))
                .flatMap(dto ->
                        ServerResponse
                                .status(HttpStatus.OK)
                                .bodyValue(dto))
                .switchIfEmpty(Mono.error(new CategoryNotFoundException(categoryId)))
                .onErrorResume(ErrorHandler::handler);

    }

}
