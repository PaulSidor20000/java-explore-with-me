package ru.practicum.ewm.locations.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import ru.practicum.ewm.exceptions.CategoryNotFoundException;
import ru.practicum.ewm.exceptions.ErrorHandler;
import ru.practicum.ewm.exceptions.LocationNotFoundException;
import ru.practicum.ewm.locations.dto.NewLocationDto;
import ru.practicum.ewm.locations.service.AdminLocationService;
import ru.practicum.ewm.utils.DtoValidator;

@Service
@RequiredArgsConstructor
public class AdminLocationHandler {
    private final AdminLocationService locationService;
    private final DtoValidator validator;

    public Mono<ServerResponse> createLocation(ServerRequest request) {
        return request.bodyToMono(NewLocationDto.class)
//                .doOnNext(validator::validate)
                .flatMap(locationService::createLocation)
                .flatMap(dto ->
                        ServerResponse
                                .status(HttpStatus.CREATED)
                                .bodyValue(dto))
                .onErrorResume(ErrorHandler::handler);
    }

    public Mono<ServerResponse> deleteLocation(ServerRequest request) {
        int locationsId = Integer.parseInt(request.pathVariable("locationsId"));

        return locationService.deleteLocation(locationsId)
                .then(ServerResponse
                        .status(HttpStatus.NO_CONTENT)
                        .body(Mono.just("Локация удалена"), String.class))
                .onErrorResume(ErrorHandler::handler);
    }

    public Mono<ServerResponse> updateLocation(ServerRequest request) {
        int locationsId = Integer.parseInt(request.pathVariable("locationsId"));

        return request.bodyToMono(NewLocationDto.class)
                .doOnNext(validator::validate)
                .flatMap(dto -> locationService.updateLocation(locationsId, dto))
                .flatMap(dto ->
                        ServerResponse
                                .status(HttpStatus.OK)
                                .bodyValue(dto))
                .switchIfEmpty(Mono.error(new LocationNotFoundException(locationsId)))
                .onErrorResume(ErrorHandler::handler);

    }

}
