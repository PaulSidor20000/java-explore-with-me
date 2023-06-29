package ru.practicum.ewm.event.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.practicum.ewm.event.dto.EventFullDto;
import ru.practicum.ewm.event.dto.EventShortDto;
import ru.practicum.ewm.event.repository.EventRepository;
import ru.practicum.statclient.client.StatClient;

@Service
@RequiredArgsConstructor
public class PublicEventServiceImpl implements PublicEventService {
    private final EventRepository eventRepository;
    private final StatClient client;

    @Override
    public Flux<EventShortDto> findEvents(MultiValueMap<String, String> params) {

        return eventRepository.getEventShortDtos(params);
    }

    @Override
    public Mono<EventFullDto> findEventById(int eventId) {

        return eventRepository.getEventFullDto(eventId);
    }

}
