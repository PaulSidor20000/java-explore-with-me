package ru.practicum.ewm.event.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.practicum.ewm.event.dto.EventFullDto;
import ru.practicum.ewm.event.dto.EventParams;
import ru.practicum.ewm.event.dto.UpdateEventAdminRequest;
import ru.practicum.ewm.event.service.AdminEventService;
import ru.practicum.ewm.locations.service.AdminLocationService;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/events")
public class AdminEventController {
    private final AdminEventService adminEventService;
    private final AdminLocationService adminLocationService;

    @GetMapping
    public Flux<EventFullDto> findEvents(@Valid @ModelAttribute EventParams params) {
        log.info("GET admin events by params={}", params);

        return adminEventService.findEvents(params);
    }

    @PatchMapping("/{eventId}")
    public Mono<EventFullDto> updateEvent(@Valid @RequestBody UpdateEventAdminRequest updateEventAdminRequest,
                                          @PathVariable Integer eventId
    ) {
        log.info("PATCH update event admin request={}, eventId={}", updateEventAdminRequest, eventId);

        return Mono.just(updateEventAdminRequest)
                .flatMap(dto -> {
                    if (dto.getLocation() != null) {
                        return adminLocationService.createLocationFromEvent(dto);
                    }
                    return Mono.just(dto);
                })
                .flatMap(dto -> adminEventService.updateEvent(eventId, dto));
    }

}
