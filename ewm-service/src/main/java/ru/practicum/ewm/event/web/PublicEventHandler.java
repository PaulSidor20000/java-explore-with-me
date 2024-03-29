package ru.practicum.ewm.event.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import ru.practicum.ewm.event.service.PublicEventService;
import ru.practicum.ewm.exceptions.BadRequestException;
import ru.practicum.ewm.exceptions.ErrorHandler;
import ru.practicum.ewm.utils.ParamsValidator;
import ru.practicum.statclient.client.StatClient;
import ru.practicum.statdto.dto.RequestDto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
@RequiredArgsConstructor
public class PublicEventHandler {
    private final PublicEventService service;
    private final StatClient client;
    private static final String EVENT_ID = "eventId";

    public Mono<ServerResponse> findEvents(ServerRequest request) {
        return Mono.just(request.queryParams())
                .doOnNext(ParamsValidator::validateParams)
                .flatMapMany(service::findEvents)
                .collectList()
                .flatMap(dto ->
                        ServerResponse
                                .status(HttpStatus.OK)
                                .bodyValue(dto))
                .onErrorResume(ErrorHandler::handler)
                .doOnSuccess(response -> hitStat(request, "/events"));
    }

    public Mono<ServerResponse> findEventById(ServerRequest request) {
        int eventId = Integer.parseInt(request.pathVariable(EVENT_ID));

        return service.findEventById(eventId)
                .flatMap(dto ->
                        ServerResponse
                                .status(HttpStatus.OK)
                                .bodyValue(dto))
                .onErrorResume(ErrorHandler::handler)
                .doOnSuccess(response -> hitStat(request, "/events/" + eventId));
    }

    private void hitStat(ServerRequest request, String uri) {
        try {
            client.post(RequestDto.builder()
                            .app("ewm-main-service")
                            .ip(request.remoteAddress().orElseThrow().getHostName())
                            .uri(uri)
                            .timestamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                            .build(), "/hit")
                    .subscribe();
        } catch (JsonProcessingException e) {
            throw new BadRequestException(e.getMessage());
        }
    }

}
