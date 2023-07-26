package ru.practicum.ewm.event.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.practicum.ewm.event.dto.*;
import ru.practicum.ewm.event.service.PrivateEventService;
import ru.practicum.ewm.locations.service.AdminLocationService;
import ru.practicum.ewm.request.dto.ParticipationRequestDto;
import ru.practicum.ewm.utils.EventValidator;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users/{userId}/events")
public class PrivateEventController {
    private final PrivateEventService privateEventService;
    private final AdminLocationService adminLocationService;
    private final EventValidator eventValidator;
    private final EventMapper mapper;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Flux<EventShortDto> findUserEvents(@PathVariable Integer userId,
                                              @RequestParam(defaultValue = "0") Integer from,
                                              @RequestParam(defaultValue = "10") Integer size
    ) {
        log.info("GET user events by userId={}, from={}, size={}", userId, from, size);

        return privateEventService.findUserEvents(userId, from, size);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<EventFullDto> createNewEvent(@PathVariable Integer userId,
                                             @Valid @RequestBody NewEventDto newEventDto
    ) {
        log.info("POST create new event={}, userId={}", newEventDto, userId);

        return Mono.just(newEventDto)
                .doOnNext(eventValidator::newEventValidator)
                .flatMap(adminLocationService::createLocationFromEvent)
                .map(dto -> mapper.merge(userId, dto))
                .flatMap(privateEventService::createNewEvent);
    }

    @GetMapping("/{eventId}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<EventFullDto> findUserEventById(@PathVariable Integer userId,
                                                @PathVariable Integer eventId
    ) {
        log.info("GET user events by eventId={}, userId={},", eventId, userId);

        return privateEventService.findUserEventById(eventId);
    }

    @GetMapping("/{eventId}/requests")
    @ResponseStatus(HttpStatus.OK)
    public Flux<ParticipationRequestDto> findRequestsOfUserEvent(@PathVariable Integer userId,
                                                                 @PathVariable Integer eventId
    ) {
        log.info("GET requests for eventId={}, userId={},", eventId, userId);

        return privateEventService.findRequestsOfUserEvent(eventId);
    }

    @PatchMapping("/{eventId}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<EventFullDto> updateUserEventById(@PathVariable Integer userId,
                                                  @PathVariable Integer eventId,
                                                  @Valid @RequestBody UpdateEventUserRequest updateEventUserRequest
    ) {
        log.info("PATCH update event user request for eventId={}, userId={}, request={}", eventId, userId, updateEventUserRequest);

        return Mono.just(updateEventUserRequest)
                .flatMap(dto -> {
                    if (dto.getLocation() != null) {
                        return adminLocationService.createLocationFromEvent(dto);
                    }
                    return Mono.just(dto);
                })
                .flatMap(dto -> privateEventService.updateUserEventById(userId, eventId, dto));
    }

    @PatchMapping("/{eventId}/requests")
    @ResponseStatus(HttpStatus.OK)
    public Mono<EventRequestStatusUpdateResult> updateRequestsOfUserEvent(
            @PathVariable Integer userId,
            @PathVariable Integer eventId,
            @Valid @RequestBody EventRequestStatusUpdateRequest eventRequestStatusUpdateRequest
    ) {
        log.info("PATCH requests of user event for eventId={}, userId={}, request={}", eventId, userId, eventRequestStatusUpdateRequest);

        return Mono.just(eventRequestStatusUpdateRequest)
                .flatMap(dto -> privateEventService.updateRequestsOfUserEvent(userId, eventId, dto));
    }

}
