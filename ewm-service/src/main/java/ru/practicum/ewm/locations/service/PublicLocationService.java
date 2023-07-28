package ru.practicum.ewm.locations.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.practicum.ewm.locations.dto.LocationDto;
import ru.practicum.ewm.locations.dto.LocationParams;


public interface PublicLocationService {

    Flux<LocationDto> findLocations(LocationParams params);

    Mono<LocationDto> findLocationById(int locationId);

}
