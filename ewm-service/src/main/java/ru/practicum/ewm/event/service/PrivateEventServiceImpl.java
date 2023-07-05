package ru.practicum.ewm.event.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.practicum.ewm.event.dto.*;
import ru.practicum.ewm.event.entity.Event;
import ru.practicum.ewm.event.repository.EventRepository;
import ru.practicum.ewm.request.dto.ParticipationRequestDto;
import ru.practicum.ewm.request.dto.RequestMapper;
import ru.practicum.ewm.request.dto.RequestStatus;
import ru.practicum.ewm.request.repository.RequestRepository;
import ru.practicum.ewm.validators.EventValidator;
import ru.practicum.ewm.validators.RequestValidator;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PrivateEventServiceImpl implements PrivateEventService {
    private final EventMapper eventMapper;
    private final RequestMapper requestMapper;
    private final RequestRepository requestRepository;
    private final EventRepository eventRepository;

    @Override
    public Flux<EventShortDto> findUserEvents(int userId, Pageable page) {
        return eventRepository.getPrivateEventShortDtos(userId, page);
    }

    @Override
    public Mono<EventFullDto> createNewEvent(Event entity) {
        return eventRepository.save(entity)
                .map(Event::getId)
                .flatMap(eventRepository::getEventFullDto);
    }

    @Override
    public Mono<EventFullDto> findUserEventById(int eventId) {
        return eventRepository.getEventFullDto(eventId);
    }

    @Override
    public Mono<EventFullDto> updateUserEventById(int userId, int eventId, UpdateEventUserRequest dto) {
        return eventRepository.findById(eventId)
                .flatMap(entity -> EventValidator.privateEventValidator(entity, dto))
                .map(entity -> eventMapper.merge(entity, dto))
                .flatMap(eventRepository::save)
                .map(Event::getId)
                .flatMap(eventRepository::getEventFullDto);
    }

    @Override
    public Flux<ParticipationRequestDto> findRequestsOfUserEvent(int eventId) {
        return requestRepository.findByEvent(eventId)
                .map(requestMapper::map);
    }

    @Override
    public Mono<EventRequestStatusUpdateResult> updateRequestsOfUserEvent(int userId, int eventId, EventRequestStatusUpdateRequest dto) {
        List<ParticipationRequestDto> confirmed = new ArrayList<>();
        List<ParticipationRequestDto> rejected = new ArrayList<>();

        return Flux.zip(requestRepository.findAllById(dto.getRequestIds())
                                .filter(RequestValidator::isRequestPending),
                        eventRepository.findByIdAndUserId(eventId, userId),
                        requestRepository.countByEventAndStatus(eventId, RequestStatus.CONFIRMED))
                .map(data -> RequestValidator.updateRequests(data.getT1(), data.getT2(), data.getT3(), dto.getStatus()))
                .flatMap(requestRepository::save)
                .map(requestMapper::map)
                .doOnNext(participationRequestDto -> {
                    if (participationRequestDto.getStatus() == RequestStatus.CONFIRMED) {
                        confirmed.add(participationRequestDto);
                    } else if (participationRequestDto.getStatus() == RequestStatus.REJECTED) {
                        rejected.add(participationRequestDto);
                    }
                })
                .then(Mono.just(EventRequestStatusUpdateResult.builder()
                        .confirmedRequests(confirmed)
                        .rejectedRequests(rejected)
                        .build()));
    }

}
