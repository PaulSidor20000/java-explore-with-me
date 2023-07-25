package ru.practicum.ewm.locations.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import ru.practicum.ewm.exceptions.ErrorHandler;
import ru.practicum.ewm.exceptions.LocationNotFoundException;
import ru.practicum.ewm.locations.service.PublicLocationService;
import ru.practicum.ewm.utils.ParamsValidator;

@Component
public class PublicLocationHandler {
//    private final PublicLocationService locationService;
//
//    @Autowired
//    public PublicLocationHandler(PublicLocationService locationService) {
//        this.locationService = locationService;
//    }
//
//    public Mono<ServerResponse> findLocations(ServerRequest request) {
//        return Mono.just(request.queryParams())
//                .doOnNext(ParamsValidator::validateParams)
//                .flatMapMany(locationService::findLocations)
//                .collectList()
//                .flatMap(dto ->
//                        ServerResponse
//                                .status(HttpStatus.OK)
//                                .bodyValue(dto))
//                .onErrorResume(ErrorHandler::handler);
//    }
//
//    public Mono<ServerResponse> findLocationById(ServerRequest request) {
//        int locationsId = Integer.parseInt(request.pathVariables().get("locationsId"));
//
//        return locationService.findLocationById(locationsId)
//                .flatMap(dto ->
//                        ServerResponse
//                                .status(HttpStatus.OK)
//                                .bodyValue(dto))
//                .switchIfEmpty(Mono.error(new LocationNotFoundException(locationsId)))
//                .onErrorResume(ErrorHandler::handler);
//    }

}
