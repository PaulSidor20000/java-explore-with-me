package ru.practicum.ewm.locations.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.practicum.ewm.locations.dto.LocationDto;
import ru.practicum.ewm.locations.dto.LocationMapper;
import ru.practicum.ewm.locations.repository.LocationRepository;


@Service
@RequiredArgsConstructor
public class PublicLocationServiceImpl implements PublicLocationService {
    private final LocationMapper locationMapper;
    private final LocationRepository locationRepository;

    @Override
    public Flux<LocationDto> findLocations(Integer radius, Float lon, Float lat, Integer from, Integer size) {
        return locationRepository.findLocationsByParams(radius, lon, lat, from, size);
    }

    @Override
    public Mono<LocationDto> findLocationById(int locationId) {
        return locationRepository.findById(locationId)
                .map(locationMapper::map);
    }

}
