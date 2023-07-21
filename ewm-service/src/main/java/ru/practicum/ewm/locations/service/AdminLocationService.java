package ru.practicum.ewm.locations.service;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import ru.practicum.ewm.event.dto.AbstractionEventDto;
import ru.practicum.ewm.locations.dto.LocationDto;
import ru.practicum.ewm.locations.dto.NewLocationDto;

@Service
public interface AdminLocationService {

    Mono<LocationDto> createLocation(NewLocationDto dto);

    <T extends AbstractionEventDto> Mono<T> createLocationFromEvent(T dto);

    Mono<Void> deleteLocation(int locationId);

}
