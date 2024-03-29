package ru.practicum.ewm.locations.repository;

import org.springframework.util.MultiValueMap;
import reactor.core.publisher.Flux;
import ru.practicum.ewm.locations.dto.LocationDto;

public interface CustomLocationRepository {

    Flux<LocationDto> findLocationsByParams(MultiValueMap<String, String> params);

}
