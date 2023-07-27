package ru.practicum.ewm.event.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.practicum.ewm.event.dto.*;
import ru.practicum.ewm.event.entity.Event;
import ru.practicum.ewm.request.dto.ParticipationRequestDto;

public interface PrivateEventService {
    Flux<EventShortDto> findUserEvents(int userId, Integer from, Integer size);

    Mono<EventFullDto> createNewEvent(Event event);

    Mono<EventFullDto> findUserEventById(int eventId);

    Mono<EventFullDto> updateUserEventById(int userId, int eventId, UpdateEventUserRequest dto);

    Flux<ParticipationRequestDto> findRequestsOfUserEvent(int eventId);

    Mono<EventRequestStatusUpdateResult> updateRequestsOfUserEvent(int userId, int eventId, EventRequestStatusUpdateRequest dto);
}
