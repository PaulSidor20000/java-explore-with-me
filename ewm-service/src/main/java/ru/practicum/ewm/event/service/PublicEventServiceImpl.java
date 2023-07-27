package ru.practicum.ewm.event.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.practicum.ewm.event.dto.EventFullDto;
import ru.practicum.ewm.event.dto.EventMapper;
import ru.practicum.ewm.event.dto.EventParams;
import ru.practicum.ewm.event.dto.EventShortDto;
import ru.practicum.ewm.event.repository.EventRepository;
import ru.practicum.ewm.exceptions.BadRequestException;
import ru.practicum.ewm.exceptions.EventNotFoundException;
import ru.practicum.ewm.utils.EventValidator;
import ru.practicum.statclient.client.StatClient;
import ru.practicum.statdto.dto.ViewStats;

import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PublicEventServiceImpl implements PublicEventService {
    private final EventRepository eventRepository;
    private final EventValidator eventValidator;
    private final ObjectMapper objectMapper;
    private final EventMapper eventMapper;
    private final StatClient client;

    @Override
    public Flux<EventShortDto> findEvents(EventParams params) {
        return Mono.just(params)
                .doOnNext(eventValidator::validateParams)
                .flatMapMany(eventRepository::getPublicEventShortDtos)
                .flatMap(dto ->
                        getHits(List.of("/events/" + dto.getId())).singleOrEmpty()
                                .map(viewStats -> eventMapper.enrich(dto, viewStats))
                );
    }

    @Override
    public Mono<EventFullDto> findEventById(int eventId) {
        return eventRepository.getPublicEventFullDto(eventId)
                .zipWith(
                        getHits(List.of("/events/" + eventId)).singleOrEmpty(),
                        eventMapper::enrich
                )
                .switchIfEmpty(Mono.error(new EventNotFoundException(eventId)));
    }

    private Flux<ViewStats> getHits(Collection<String> uris) {
        return client.get(uris, true)
                .map(this::getViewStats)
                .flatMap(Flux::fromIterable);
    }

    private List<ViewStats> getViewStats(String statJson) {
        if (statJson == null || statJson.equals("[]")) {
            return List.of(ViewStats.builder().app(null).uri(null).hits(null).build());
        }
        try {
            return objectMapper.readValue(statJson, new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            throw new BadRequestException(e.getMessage());
        }
    }

}


