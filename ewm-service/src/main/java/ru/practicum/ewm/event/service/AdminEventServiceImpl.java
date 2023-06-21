package ru.practicum.ewm.event.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.practicum.ewm.category.reposytory.CategoryRepository;
import ru.practicum.ewm.event.dto.EventFullDto;
import ru.practicum.ewm.event.dto.EventMapper;
import ru.practicum.ewm.event.dto.UpdateEventAdminRequest;
import ru.practicum.ewm.event.entity.Event;
import ru.practicum.ewm.event.repository.EventRepository;
import ru.practicum.ewm.user.repository.UserRepository;
import ru.practicum.ewm.validators.DtoValidator;
import ru.practicum.ewm.validators.EventValidator;

@Service
@RequiredArgsConstructor
public class AdminEventServiceImpl implements AdminEventService {
    private final EventMapper mapper;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public Flux<EventFullDto> findEvent(MultiValueMap<String, String> params) {
        return eventRepository.getEventFullDtos(params);
    }

    @Override
    public Mono<EventFullDto> patchEvent(int eventId, UpdateEventAdminRequest dto) {
        return eventRepository.findById(eventId)
                .flatMap(entity -> EventValidator.adminEventValidator(entity, dto))
                .map(entity -> mapper.merge(entity, dto))
                .flatMap(eventRepository::save)
                .map(Event::getId)
                .flatMap(eventRepository::getEventFullDto);

    }

}
