package ru.practicum.ewm.category;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import ru.practicum.ewm.dto.CategoryDto;
import ru.practicum.ewm.exceptions.DataNotFoundException;
import ru.practicum.ewm.exceptions.ErrorHandler;
import ru.practicum.ewm.validators.DtoValidator;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class AdminCategoryHandler {
    private final CategoryRepository repository;
    private final CategoryMapper mapper;
    private final DtoValidator validator;

    public Mono<ServerResponse> createCategory(ServerRequest request) {
        return request.bodyToMono(CategoryDto.class)
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

    public Mono<ServerResponse> deleteCategory(ServerRequest request) {
        int id = Integer.parseInt(request.pathVariable("id"));

        return repository.deleteById(id)
                .then(ServerResponse
                        .status(HttpStatus.NO_CONTENT)
                        .body(Mono.just("Категория удалена"), String.class))
                .switchIfEmpty(Mono.error(new DataNotFoundException(format("Category with id=%d was not found", id))))
                .onErrorResume(ErrorHandler::handler);
    }

    public Mono<ServerResponse> patchCategory(ServerRequest request) {
        int id = Integer.parseInt(request.pathVariable("id"));

        return repository.findById(id)
                .flatMap(category ->
                        request.bodyToMono(CategoryDto.class)
                                .map(dto -> mapper.merge(category, dto))
                                .flatMap(repository::save)
                                .map(mapper::map))
                .flatMap(dto ->
                        ServerResponse
                                .status(HttpStatus.OK)
                                .bodyValue(dto))
                .switchIfEmpty(Mono.error(new DataNotFoundException(format("Category with id=%d was not found", id))))
                .onErrorResume(ErrorHandler::handler);

    }

}
