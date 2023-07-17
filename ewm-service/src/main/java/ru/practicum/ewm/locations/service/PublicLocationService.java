package ru.practicum.ewm.locations.service;

import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.practicum.ewm.locations.dto.LocationDto;

@Service
public interface PublicLocationService {

    Flux<LocationDto> findLocations(MultiValueMap<String, String> params);

    Mono<LocationDto> findLocationById(int locationId);

}
