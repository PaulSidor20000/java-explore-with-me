package ru.practicum.ewm.event.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.util.MultiValueMap;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.practicum.ewm.event.dto.EventFullDto;
import ru.practicum.ewm.event.dto.EventParams;
import ru.practicum.ewm.event.dto.EventShortDto;

public interface CustomEventRepository {

    Mono<EventFullDto> getEventFullDto(int eventId);

    Mono<EventFullDto> getPublicEventFullDto(int eventId);

    Flux<EventFullDto> getAdminEventFullDtos(EventParams params);

    Flux<EventShortDto> getPrivateEventShortDtos(int userId, Integer from, Integer size);

    Flux<EventShortDto> getPublicEventShortDtos(EventParams params);
}
