package ru.practicum.ewm.request.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.practicum.ewm.request.dto.ParticipationRequestDto;
import ru.practicum.ewm.request.service.PrivateRequestService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users/{userId}/requests")
public class PrivateRequestController {
    private final PrivateRequestService privateRequestService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Flux<ParticipationRequestDto> findUserRequests(@PathVariable Integer userId) {
        log.info("GET user's requests userId={}", userId);

        return privateRequestService.findUserRequests(userId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ParticipationRequestDto> createNewRequest(@PathVariable Integer userId,
                                                          @RequestParam Integer eventId
    ) {
        log.info("POST create new request userId={}, eventId={}", userId, eventId);

        return privateRequestService.createNewRequest(userId, eventId);
    }

    @PatchMapping("/{requestId}/cancel")
    @ResponseStatus(HttpStatus.OK)
    public Mono<ParticipationRequestDto> cancelUserRequest(@PathVariable Integer userId,
                                                           @PathVariable Integer requestId
    ) {
        log.info("PATCH cancel request userId={}, requestId={}", userId, requestId);

        return privateRequestService.cancelUserRequest(userId, requestId);
    }

}
