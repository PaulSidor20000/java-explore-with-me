package ru.practicum.ewm.event.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import ru.practicum.ewm.event.dto.EventFullDto;
import ru.practicum.ewm.event.dto.EventParams;
import ru.practicum.ewm.event.dto.EventShortDto;
import ru.practicum.ewm.event.service.PublicEventService;
import ru.practicum.ewm.exceptions.BadRequestException;
import ru.practicum.statclient.client.StatClient;
import ru.practicum.statdto.dto.RequestDto;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/events")
public class PublicEventController {
    private final PublicEventService publicEventService;
    private final StatClient client;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Mono<List<EventShortDto>> findEvents(@Valid @ModelAttribute EventParams params,
                                                ServerHttpRequest request
    ) {
        log.info("GET event by params={}", params);

        return publicEventService.findEvents(params)
                .collectList()
                .doOnSuccess(response -> hitStat(request, "/events"));
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<EventFullDto> findEventById(@PathVariable Integer id,
                                            ServerHttpRequest request
    ) {
        log.info("GET event by id={}", id);

        return publicEventService.findEventById(id)
                .doOnSuccess(response -> hitStat(request, "/events/" + id));
    }

    private void hitStat(ServerHttpRequest request, String uri) {
        try {
            client.post(RequestDto.builder()
                            .app("ewm-main-service")
                            .ip(Objects.requireNonNull(request.getRemoteAddress()).getAddress().getHostAddress())
                            .uri(uri)
                            .timestamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                            .build(), "/hit")
                    .subscribe();
        } catch (JsonProcessingException e) {
            throw new BadRequestException(e.getMessage());
        }
    }

}
