package ru.practicum.ewm.event.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.util.MultiValueMap;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.practicum.ewm.event.dto.EventFullDto;
import ru.practicum.ewm.event.dto.EventShortDto;

public interface CustomEventRepository {

    Mono<EventFullDto> getEventFullDto(int eventId);

    Flux<EventFullDto> getEventFullDtos(MultiValueMap<String, String> params);

    Flux<EventShortDto> getEventShortDtos(int userId, Pageable page);
}
