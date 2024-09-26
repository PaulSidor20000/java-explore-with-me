package ru.practicum.ewm.locations.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import ru.practicum.ewm.event.dto.AbstractionEventDto;
import ru.practicum.ewm.event.repository.EventRepository;
import ru.practicum.ewm.exceptions.BadRequestException;
import ru.practicum.ewm.exceptions.LocationConditionException;
import ru.practicum.ewm.locations.dto.LocationDto;
import ru.practicum.ewm.locations.dto.LocationMapper;
import ru.practicum.ewm.locations.dto.NewLocationDto;
import ru.practicum.ewm.locations.entity.AbstractLocation;
import ru.practicum.ewm.locations.repository.LocationRepository;
import ru.practicum.geoclient.client.GeoClient;
import ru.practicum.geoclient.client.model.GeoData;

@Service
@RequiredArgsConstructor
public class AdminLocationServiceImpl implements AdminLocationService {
    private final LocationRepository locationRepository;
    private final EventRepository eventRepository;
    private final LocationMapper locationMapper;
    private final GeoClient geoClient;

    @Override
    public Mono<LocationDto> createLocation(NewLocationDto dto) {
        return getGeoData(dto)
                .map(locationMapper::map)
                .flatMap(locationRepository::save)
                .map(locationMapper::map)
                .switchIfEmpty(Mono.error(new BadRequestException("Wrong request for getting geo data")));
    }

    @Override
    public <T extends AbstractionEventDto> Mono<T> createLocationFromEvent(T dto) {
        return getGeoData(dto.getLocation())
                .map(geoData -> {
                    geoData.setLon(dto.getLocation().getLon());
                    geoData.setLat(dto.getLocation().getLat());
                    return geoData;
                })
                .map(locationMapper::map)
                .flatMap(locationRepository::save)
                .map(location -> {
                    dto.getLocation().setId(location.getId());
                    dto.getLocation().setName(location.getName());
                    return dto;
                })
                .switchIfEmpty(Mono.error(new BadRequestException("Wrong request for getting geo data")));
    }

    @Override
    public Mono<Void> deleteLocation(int locationId) {
        return eventRepository.findByLocationId(locationId)
                .handle((event, sink) -> {
                    if (event != null) {
                        sink.error(new LocationConditionException(event));
                    }
                })
                .switchIfEmpty(locationRepository.deleteById(locationId)).then();
    }

    private <T extends AbstractLocation> Mono<GeoData> getGeoData(T dto) {
        if (dto.getName() != null && dto.getLon() != null && dto.getLat() != null) {
            return Mono.just(locationMapper.map(dto));
        }
        if (dto.getLon() != null && dto.getLat() != null) {
            return geoClient.get(dto.getLon(), dto.getLat());
        }
        if (dto.getName() != null) {
            return geoClient.get(dto.getName());
        } else {
            return Mono.empty();
        }
    }

}
