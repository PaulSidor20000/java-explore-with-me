package ru.practicum.ewm.locations.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import ru.practicum.ewm.event.dto.NewEventDto;
import ru.practicum.ewm.event.entity.Event;
import ru.practicum.ewm.event.repository.EventRepository;
import ru.practicum.ewm.exceptions.BadRequestException;
import ru.practicum.ewm.exceptions.LocationConditionException;
import ru.practicum.ewm.locations.dto.LocationDto;
import ru.practicum.ewm.locations.dto.LocationMapper;
import ru.practicum.ewm.locations.dto.NewLocationDto;
import ru.practicum.ewm.locations.entity.Location;
import ru.practicum.ewm.locations.repository.LocationRepository;
import ru.practicum.geoclient.client.GeoClient;
import ru.practicum.geoclient.client.model.GeoData;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AdminLocationServiceImplTest {
    @Mock
    private LocationRepository locationRepository;
    @Mock
    private EventRepository eventRepository;
    @Mock
    private LocationMapper locationMapper;
    @Mock
    private GeoClient geoClient;
    @InjectMocks
    private AdminLocationServiceImpl adminLocationService;
    private final NewLocationDto newLocationDtoEmpty = NewLocationDto.builder().build();
    private final NewLocationDto newLocationDtoName = NewLocationDto.builder().name("test").build();
    private final NewLocationDto newLocationDtoCoordinates = NewLocationDto.builder().lon(0F).lat(0F).build();
    private final NewLocationDto newLocationDtoFull = NewLocationDto.builder().lon(0F).lat(0F).name("test").build();
    private final LocationDto locationDtoEmpty = LocationDto.builder().build();
    private final LocationDto locationDtoName = LocationDto.builder().name("test").build();
    private final LocationDto locationDtoCoordinates = LocationDto.builder().lon(0F).lat(0F).build();
    private final LocationDto locationDtoFull = LocationDto.builder().lon(0F).lat(0F).name("test").build();
    private final Location location = Location.builder().id(1).lon(0F).lat(0F).name("test").build();
    private final GeoData geoData = GeoData.builder().lon(0F).lat(0F).name("test").build();

    @Test
    void createLocationWithFullGeoData() {
        when(locationMapper.map(any(GeoData.class))).thenReturn(location);
        when(locationMapper.map(any(NewLocationDto.class))).thenReturn(geoData);
        when(locationMapper.map(any(Location.class))).thenReturn(locationDtoFull);
        when(locationRepository.save(any(Location.class))).thenReturn(Mono.just(location));

        adminLocationService.createLocation(newLocationDtoFull)
                .as(StepVerifier::create)
                .consumeNextWith(dto -> assertEquals(locationDtoFull, dto))
                .verifyComplete();
    }

    @Test
    void createLocationWithCoordinatesGeoData() {
        when(locationMapper.map(any(GeoData.class))).thenReturn(location);
        when(locationMapper.map(any(Location.class))).thenReturn(locationDtoFull);
        when(geoClient.get(anyFloat(), anyFloat())).thenReturn(Mono.just(geoData));
        when(locationRepository.save(any(Location.class))).thenReturn(Mono.just(location));

        adminLocationService.createLocation(newLocationDtoCoordinates)
                .as(StepVerifier::create)
                .consumeNextWith(dto -> assertEquals(locationDtoFull, dto))
                .verifyComplete();
    }

    @Test
    void createLocationWithNameGeoData() {
        when(locationMapper.map(any(GeoData.class))).thenReturn(location);
        when(locationMapper.map(any(Location.class))).thenReturn(locationDtoFull);
        when(geoClient.get(anyString())).thenReturn(Mono.just(geoData));
        when(locationRepository.save(any(Location.class))).thenReturn(Mono.just(location));

        adminLocationService.createLocation(newLocationDtoName)
                .as(StepVerifier::create)
                .consumeNextWith(dto -> assertEquals(locationDtoFull, dto))
                .verifyComplete();
    }

    @Test
    void createLocationWithNoGeoData() {
        adminLocationService.createLocation(newLocationDtoEmpty)
                .as(StepVerifier::create)
                .expectError(BadRequestException.class)
                .verify();
    }

    @Test
    void createLocationFromEventWithFullGeoData() {
        NewEventDto event = NewEventDto.builder().location(locationDtoFull).build();

        when(locationMapper.map(any(GeoData.class))).thenReturn(location);
        when(locationMapper.map(any(LocationDto.class))).thenReturn(geoData);
        when(locationRepository.save(any(Location.class))).thenReturn(Mono.just(location));

        adminLocationService.createLocationFromEvent(event)
                .as(StepVerifier::create)
                .consumeNextWith(dto -> {
                    assertEquals(1, dto.getLocation().getId());
                    assertEquals("test", dto.getLocation().getName());
                    assertEquals(0.0F, dto.getLocation().getLon());
                    assertEquals(0.0F, dto.getLocation().getLat());
                })
                .verifyComplete();
    }

    @Test
    void createLocationFromEventWithCoordinatesGeoData() {
        NewEventDto event = NewEventDto.builder().location(locationDtoCoordinates).build();

        when(locationMapper.map(any(GeoData.class))).thenReturn(location);
        when(geoClient.get(anyFloat(), anyFloat())).thenReturn(Mono.just(geoData));
        when(locationRepository.save(any(Location.class))).thenReturn(Mono.just(location));

        adminLocationService.createLocationFromEvent(event)
                .as(StepVerifier::create)
                .consumeNextWith(dto -> {
                    assertEquals(1, dto.getLocation().getId());
                    assertEquals("test", dto.getLocation().getName());
                    assertEquals(0.0F, dto.getLocation().getLon());
                    assertEquals(0.0F, dto.getLocation().getLat());
                })
                .verifyComplete();
    }

    @Test
    void createLocationFromEventWithNameGeoData() {
        NewEventDto event = NewEventDto.builder().location(locationDtoName).build();

        when(locationMapper.map(any(GeoData.class))).thenReturn(location);
        when(geoClient.get(anyString())).thenReturn(Mono.just(geoData));
        when(locationRepository.save(any(Location.class))).thenReturn(Mono.just(location));

        adminLocationService.createLocationFromEvent(event)
                .as(StepVerifier::create)
                .consumeNextWith(dto -> {
                    assertEquals(1, dto.getLocation().getId());
                    assertEquals("test", dto.getLocation().getName());
                })
                .verifyComplete();
    }

    @Test
    void createLocationFromEventWithNoGeoData() {
        NewEventDto event = NewEventDto.builder().location(locationDtoEmpty).build();

        adminLocationService.createLocationFromEvent(event)
                .as(StepVerifier::create)
                .expectError(BadRequestException.class)
                .verify();
    }

    @Test
    void deleteLocationWhenEventAssigned() {
        when(eventRepository.findByLocationId(anyInt())).thenReturn(Flux.just(new Event()));
        when(locationRepository.deleteById(anyInt())).thenReturn(Mono.empty());

        adminLocationService.deleteLocation(1)
                .as(StepVerifier::create)
                .expectError(LocationConditionException.class)
                .verify();
    }

    @Test
    void deleteLocationWhenEventNotAssigned() {
        when(eventRepository.findByLocationId(anyInt())).thenReturn(Flux.empty());
        when(locationRepository.deleteById(anyInt())).thenReturn(Mono.empty());

        adminLocationService.deleteLocation(1)
                .as(StepVerifier::create)
                .verifyComplete();
    }

}