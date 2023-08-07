package ru.practicum.ewm.locations.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import ru.practicum.ewm.exceptions.NotFoundException;
import ru.practicum.ewm.locations.dto.LocationDto;
import ru.practicum.ewm.locations.dto.LocationMapper;
import ru.practicum.ewm.locations.dto.LocationParams;
import ru.practicum.ewm.locations.entity.Location;
import ru.practicum.ewm.locations.repository.LocationRepository;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PublicLocationServiceImplTest {
    @Mock
    private LocationMapper locationMapper;
    @Mock
    private LocationRepository locationRepository;
    @InjectMocks
    private PublicLocationServiceImpl publicLocationService;
    private final LocationDto locationDto = LocationDto.builder().id(1).lon(0F).lat(0F).name("test").build();
    private final Location location = Location.builder().id(1).lon(0F).lat(0F).name("test").build();
    private final LocationParams params = LocationParams.builder().lon(0F).lat(0F).radius(0).build();

    @Test
    void findLocationsByParams() {
        when(locationRepository.findLocationsByParams(any(LocationParams.class))).thenReturn(Flux.just(locationDto));

        publicLocationService.findLocations(params)
                .as(StepVerifier::create)
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    void findLocationById() {
        when(locationRepository.findById(anyInt())).thenReturn(Mono.just(location));
        when(locationMapper.map(any(Location.class))).thenReturn(locationDto);

        publicLocationService.findLocationById(1)
                .as(StepVerifier::create)
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    void findLocationByIdNotFound() {
        when(locationRepository.findById(anyInt())).thenReturn(Mono.empty());

        publicLocationService.findLocationById(1)
                .as(StepVerifier::create)
                .expectError(NotFoundException.class)
                .verify();
    }

}