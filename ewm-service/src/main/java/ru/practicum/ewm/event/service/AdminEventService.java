package ru.practicum.ewm.event.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.practicum.ewm.event.dto.EventFullDto;
import ru.practicum.ewm.event.dto.EventParams;
import ru.practicum.ewm.event.dto.UpdateEventAdminRequest;

public interface AdminEventService {
    Flux<EventFullDto> findEvents(EventParams params);

    Mono<EventFullDto> updateEvent(int eventId, UpdateEventAdminRequest dto);
}
