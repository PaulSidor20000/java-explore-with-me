package ru.practicum.ewm.locations.service;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import reactor.core.publisher.Mono;
import ru.practicum.ewm.event.repository.EventRepository;
import ru.practicum.ewm.locations.dto.LocationDto;
import ru.practicum.ewm.locations.dto.LocationMapper;
import ru.practicum.ewm.locations.dto.NewLocationDto;
import ru.practicum.ewm.locations.entity.Location;
import ru.practicum.ewm.locations.repository.LocationRepository;
import ru.practicum.geoclient.client.GeoClient;
import ru.practicum.geoclient.client.model.GeoData;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyFloat;
import static org.mockito.Mockito.any;
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
    private final NewLocationDto newLocationDto = NewLocationDto.builder().lon(0F).lat(0F).build();
    private final Location location = Location.builder().id(1).lon(0F).lat(0F).name("test").build();
    private final LocationDto locationDto = LocationDto.builder().id(1).lon(0F).lat(0F).name("test").build();
    private final GeoData geoData = GeoData.builder().lon(0F).lat(0F).name("test").build();

    @BeforeEach
    void setUp() {

    }

    @Test
    void createLocation() {
        when(geoClient.get(anyFloat(), anyFloat())).thenReturn(Mono.just(geoData));
//        when(locationRepository.save(new Location())).thenReturn(Mono.just(location));

        adminLocationService.createLocation(newLocationDto)
                .subscribe(dto -> assertEquals(geoData, dto));
    }

    @Test
    void createLocationFromEvent() {
    }

    @Test
    void deleteLocation() {
    }
}