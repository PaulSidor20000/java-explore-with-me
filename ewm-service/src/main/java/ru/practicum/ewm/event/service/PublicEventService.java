package ru.practicum.ewm.event.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.practicum.ewm.event.dto.EventFullDto;
import ru.practicum.ewm.event.dto.EventParams;
import ru.practicum.ewm.event.dto.EventShortDto;

public interface PublicEventService {
    Flux<EventShortDto> findEvents(EventParams params);

    Mono<EventFullDto> findEventById(int eventId);

}