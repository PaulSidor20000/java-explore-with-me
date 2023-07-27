package ru.practicum.ewm.event.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.practicum.ewm.event.dto.EventFullDto;
import ru.practicum.ewm.event.dto.EventMapper;
import ru.practicum.ewm.event.dto.EventParams;
import ru.practicum.ewm.event.dto.UpdateEventAdminRequest;
import ru.practicum.ewm.event.entity.Event;
import ru.practicum.ewm.event.repository.EventRepository;
import ru.practicum.ewm.utils.EventValidator;

@Service
@RequiredArgsConstructor
public class AdminEventServiceImpl implements AdminEventService {
    private final EventMapper mapper;
    private final EventRepository eventRepository;
    private final EventValidator eventValidator;

    @Override
    public Flux<EventFullDto> findEvents(EventParams params) {
        return Mono.just(params)
                .doOnNext(eventValidator::validateParams)
                .flatMapMany(eventRepository::getAdminEventFullDtos);
    }

    @Override
    public Mono<EventFullDto> updateEvent(int eventId, UpdateEventAdminRequest dto) {
        return eventRepository.findById(eventId)
                .flatMap(entity -> eventValidator.adminUpdateEventAdminRequestValidator(entity, dto))
                .map(entity -> mapper.merge(entity, dto))
                .flatMap(eventRepository::save)
                .map(Event::getId)
                .flatMap(eventRepository::getEventFullDto);
    }

}
