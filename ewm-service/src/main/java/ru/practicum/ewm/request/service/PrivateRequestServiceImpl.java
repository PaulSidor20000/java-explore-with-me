package ru.practicum.ewm.request.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.practicum.ewm.event.repository.EventRepository;
import ru.practicum.ewm.exceptions.BadRequestException;
import ru.practicum.ewm.exceptions.RequestConditionException;
import ru.practicum.ewm.request.dto.ParticipationRequestDto;
import ru.practicum.ewm.request.dto.RequestMapper;
import ru.practicum.ewm.request.dto.RequestStatus;
import ru.practicum.ewm.request.entity.Request;
import ru.practicum.ewm.request.repository.RequestRepository;
import ru.practicum.ewm.utils.RequestValidator;

@Service
@RequiredArgsConstructor
public class PrivateRequestServiceImpl implements PrivateRequestService {
    private final RequestMapper mapper;
    private final RequestRepository requestRepository;
    private final EventRepository eventRepository;

    @Override
    public Mono<ParticipationRequestDto> createNewRequest(int userId, int eventId) {
        if (eventId == -1) {
            return Mono.error(new BadRequestException("Missed query parameter: eventId"));
        }
        return requestRepository.findByRequesterAndEvent(userId, eventId)
                .doOnSuccess(this::doThrow)
                .then(eventRepository.findById(eventId))
                .zipWith(requestRepository.countByEventAndStatus(eventId, RequestStatus.CONFIRMED),
                        (event, confirmedRequests) ->
                                RequestValidator.incomingRequestValidator(userId, confirmedRequests, event))
                .map(event -> mapper.merge(userId, event))
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
                .map(this::doCancel)
                .flatMap(requestRepository::save)
                .map(mapper::map);
    }

    private void doThrow(Request request) {
        if (request != null) {
            throw new RequestConditionException(request);
        }
    }

    private Request doCancel(Request request) {
        if (request.getStatus() == RequestStatus.PENDING) {
            request.setStatus(RequestStatus.CANCELED);
            return request;
        }
        throw new RequestConditionException("Impossible to cancel published request");
    }

}
