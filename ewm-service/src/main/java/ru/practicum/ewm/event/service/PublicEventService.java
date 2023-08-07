package ru.practicum.ewm.event.service;

import reactor.core.publisher.Mono;
import ru.practicum.ewm.event.dto.EventFullDto;
import ru.practicum.ewm.event.dto.EventParams;
import ru.practicum.ewm.event.dto.EventShortDto;

import java.util.List;

public interface PublicEventService {
    Mono<List<EventShortDto>> findEvents(EventParams params);

    Mono<EventFullDto> findEventById(int eventId);

}