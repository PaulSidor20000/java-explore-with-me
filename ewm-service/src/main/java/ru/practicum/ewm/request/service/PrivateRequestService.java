package ru.practicum.ewm.request.service;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.practicum.ewm.request.dto.ParticipationRequestDto;

@Service
public interface PrivateRequestService {

    Mono<ParticipationRequestDto> createNewRequest(int userId, int eventId);

    Flux<ParticipationRequestDto> findUserRequests(int userId);

    Mono<ParticipationRequestDto> cancelUserRequest(int userId, int requestId);

}
