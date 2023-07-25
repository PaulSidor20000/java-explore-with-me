package ru.practicum.ewm.locations.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import ru.practicum.ewm.exceptions.ErrorHandler;
import ru.practicum.ewm.locations.dto.NewLocationDto;
import ru.practicum.ewm.locations.service.AdminLocationService;

@Component
@RequiredArgsConstructor
public class AdminLocationHandler {
//    private final AdminLocationService locationService;
//
//    public Mono<ServerResponse> createLocation(ServerRequest request) {
//        return request.bodyToMono(NewLocationDto.class)
//                .flatMap(locationService::createLocation)
//                .flatMap(dto ->
//                        ServerResponse
//                                .status(HttpStatus.CREATED)
//                                .bodyValue(dto))
//                .onErrorResume(ErrorHandler::handler);
//    }
//
//    public Mono<ServerResponse> deleteLocation(ServerRequest request) {
//        int locationsId = Integer.parseInt(request.pathVariable("locationsId"));
//
//        return locationService.deleteLocation(locationsId)
//                .then(ServerResponse
//                        .status(HttpStatus.NO_CONTENT)
//                        .body(Mono.just("Локация удалена"), String.class))
//                .onErrorResume(ErrorHandler::handler);
//    }

}
