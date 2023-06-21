package ru.practicum.ewm.category.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import ru.practicum.ewm.category.dto.CategoryMapper;
import ru.practicum.ewm.category.dto.NewCategoryDto;
import ru.practicum.ewm.category.reposytory.CategoryRepository;
import ru.practicum.ewm.category.dto.CategoryDto;
import ru.practicum.ewm.exceptions.CategoryNotFoundException;
import ru.practicum.ewm.exceptions.ErrorHandler;
import ru.practicum.ewm.validators.DtoValidator;

@Service
@RequiredArgsConstructor
public class AdminCategoryHandler {
    private final CategoryRepository repository;
    private final CategoryMapper mapper;
    private final DtoValidator validator;

    public Mono<ServerResponse> createCategory(ServerRequest request) {
        return request.bodyToMono(NewCategoryDto.class)
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

    public Mono<ServerResponse> deleteCategory(ServerRequest request) {
        int categoryId = Integer.parseInt(request.pathVariable("id"));

        return repository.deleteById(categoryId)
                .then(ServerResponse
                        .status(HttpStatus.NO_CONTENT)
                        .body(Mono.just("Категория удалена"), String.class))
                .switchIfEmpty(Mono.error(new CategoryNotFoundException(categoryId)))
                .onErrorResume(ErrorHandler::handler);
    }

    public Mono<ServerResponse> patchCategory(ServerRequest request) {
        int categoryId = Integer.parseInt(request.pathVariable("id"));

        return repository.findById(categoryId)
                .flatMap(category ->
                        request.bodyToMono(NewCategoryDto.class)
                                .map(dto -> mapper.merge(category, dto))
                                .flatMap(repository::save)
                                .map(mapper::map))
                .flatMap(dto ->
                        ServerResponse
                                .status(HttpStatus.OK)
                                .bodyValue(dto))
                .switchIfEmpty(Mono.error(new CategoryNotFoundException(categoryId)))
                .onErrorResume(ErrorHandler::handler);

    }

}
