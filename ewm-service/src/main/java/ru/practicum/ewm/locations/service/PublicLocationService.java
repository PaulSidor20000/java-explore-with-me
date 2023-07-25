package ru.practicum.ewm.locations.service;

import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.practicum.ewm.locations.dto.LocationDto;


public interface PublicLocationService {

    Flux<LocationDto> findLocations(Integer radius, Float lon, Float lat, Integer from, Integer size);

    Mono<LocationDto> findLocationById(int locationId);

}
