package ru.practicum.ewm.locations.service;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import ru.practicum.ewm.event.dto.NewEventDto;
import ru.practicum.ewm.locations.dto.LocationDto;
import ru.practicum.ewm.locations.dto.NewLocationDto;

@Service
public interface AdminLocationService {

    Mono<LocationDto> createLocation(NewLocationDto dto);

    Mono<NewEventDto> createLocationFromEvent(NewEventDto dto);

    Mono<Void> deleteLocation(int locationId);

    Mono<LocationDto> updateLocation(int locationId, NewLocationDto dto);

}
