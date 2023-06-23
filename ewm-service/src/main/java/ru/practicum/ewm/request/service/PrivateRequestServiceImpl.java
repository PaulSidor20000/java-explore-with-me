package ru.practicum.ewm.request.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.practicum.ewm.event.entity.Event;
import ru.practicum.ewm.event.repository.EventRepository;
import ru.practicum.ewm.request.dto.ParticipationRequestDto;
import ru.practicum.ewm.request.dto.RequestMapper;
import ru.practicum.ewm.request.dto.RequestStatus;
import ru.practicum.ewm.request.repository.RequestRepository;
import ru.practicum.ewm.validators.RequestValidator;

@Service
@RequiredArgsConstructor
public class PrivateRequestServiceImpl implements PrivateRequestService {
    private final RequestMapper mapper;
    private final RequestRepository requestRepository;
    private final EventRepository eventRepository;

    @Override
    public Mono<ParticipationRequestDto> createNewRequest(int userId, int eventId) {
        return requestRepository.findByRequesterAndEvent(userId, eventId)
                .doOnSuccess(RequestValidator::doThrow)
                .then(eventRepository.findById(eventId))
                .zipWith(requestRepository.countByEventAndStatus(eventId, RequestStatus.CONFIRMED),
                        (event, confirmedRequests) -> RequestValidator.incomingRequestValidator(userId, confirmedRequests, event))
                .map(Event::isRequestModeration)
                .flatMap(isRequestModeration -> Mono.just(mapper.merge(userId, eventId, isRequestModeration)))
                .flatMap(requestRepository::save)
                .map(mapper::map);
    }

    @Override
    public Flux<ParticipationRequestDto> findUserRequests(int userId) {
        return requestRepository.findByRequester(userId)
                .map(mapper::map);
    }

    @Override
    public Mono<ParticipationRequestDto> cancelUserRequest(int userId, int requestId) {
        return requestRepository.findByRequesterAndId(userId, requestId)
                .map(RequestValidator::doCancel)
                .flatMap(requestRepository::save)
                .map(mapper::map);
    }

}
