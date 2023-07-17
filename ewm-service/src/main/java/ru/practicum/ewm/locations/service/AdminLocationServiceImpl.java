package ru.practicum.ewm.locations.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.practicum.ewm.event.entity.Event;
import ru.practicum.ewm.event.repository.EventRepository;
import ru.practicum.ewm.exceptions.CategoryConditionException;
import ru.practicum.ewm.locations.dto.LocationDto;
import ru.practicum.ewm.locations.dto.LocationMapper;
import ru.practicum.ewm.locations.dto.NewLocationDto;
import ru.practicum.ewm.locations.repository.LocationRepository;

@Service
@RequiredArgsConstructor
public class AdminLocationServiceImpl implements AdminLocationService {
    private final LocationMapper locationMapper;
    private final LocationRepository locationRepository;
    private final EventRepository eventRepository;
    private final WebClient webClient;

    @Override
    public Mono<LocationDto> createLocation(NewLocationDto dto) {


        return Mono.just(locationMapper.map(dto))
                .flatMap(locationRepository::save)
                .map(locationMapper::map);
    }

    @Override
    public Mono<Void> deleteLocation(int locationId) {
        return eventRepository.findByLocationId(locationId)
                .doOnNext(this::doThrow)
                .then(locationRepository.deleteById(locationId));
    }

    @Override
    public Mono<LocationDto> updateLocation(int locationId, NewLocationDto dto) {
        return locationRepository.findById(locationId)
                .map(location -> locationMapper.merge(location, dto))
                .flatMap(locationRepository::save)
                .map(locationMapper::map);
    }

    private void doThrow(Event event) {
        if (event != null) {
            throw new CategoryConditionException(event);
        }
    }

}
