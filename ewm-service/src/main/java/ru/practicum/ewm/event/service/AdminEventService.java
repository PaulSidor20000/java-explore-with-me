package ru.practicum.ewm.event.service;

import org.springframework.util.MultiValueMap;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.practicum.ewm.event.dto.EventFullDto;
import ru.practicum.ewm.event.dto.UpdateEventAdminRequest;

public interface AdminEventService {
    Flux<EventFullDto> findEvents(MultiValueMap<String, String> params);

    Mono<EventFullDto> updateEvent(int eventId, UpdateEventAdminRequest dto);
}
