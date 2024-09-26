package ru.practicum.ewm.locations.repository;

import reactor.core.publisher.Flux;
import ru.practicum.ewm.locations.dto.LocationDto;
import ru.practicum.ewm.locations.dto.LocationParams;


public interface CustomLocationRepository {

    Flux<LocationDto> findLocationsByParams(LocationParams params);

}
