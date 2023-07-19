package ru.practicum.ewm.locations.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import ru.practicum.ewm.event.entity.Event;
import ru.practicum.ewm.event.repository.EventRepository;
import ru.practicum.ewm.exceptions.CategoryConditionException;
import ru.practicum.ewm.locations.dto.LocationDto;
import ru.practicum.ewm.locations.dto.LocationMapper;
import ru.practicum.ewm.locations.dto.NewLocationDto;
import ru.practicum.ewm.locations.repository.LocationRepository;
import ru.practicum.geoclient.client.GeoClient;
import ru.practicum.geoclient.client.model.GeoData;

@Service
@RequiredArgsConstructor
public class AdminLocationServiceImpl implements AdminLocationService {
    private final LocationMapper locationMapper;
    private final LocationRepository locationRepository;
    private final EventRepository eventRepository;
    private final GeoClient geoClient;

    @Override
    public Mono<LocationDto> createLocation(NewLocationDto dto) {
        return getGeoData(dto)
                .map(locationMapper::map)
                .flatMap(locationRepository::save)
                .map(locationMapper::map);
    }

    private Mono<GeoData> getGeoData(NewLocationDto dto) {
//        if (dto.getName() != null && dto.getLon() != null && dto.getLat() != null) {
//            return Mono.just(locationMapper.map(dto));
//        }
        return dto.getName() != null ? geoClient.get(dto.getName()) : geoClient.get(dto.getLon(), dto.getLat());
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
