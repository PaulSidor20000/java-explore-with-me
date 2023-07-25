package ru.practicum.ewm.locations.web;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.practicum.ewm.locations.controllers.PublicLocationController;
import ru.practicum.ewm.locations.dto.LocationDto;
import ru.practicum.ewm.locations.service.PublicLocationService;

import static org.mockito.ArgumentMatchers.anyFloat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@WebFluxTest(controllers = PublicLocationController.class)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class PublicLocationControllerTest {
    private final WebTestClient client;
    @MockBean
    private PublicLocationService publicLocationService;
    private final LocationDto locationDto = LocationDto.builder().id(1).name("Москва").lon(100F).lat(150F).build();

    @Test
    @DisplayName("GET /locations should return array of LocationDtos")
    void findLocations() {
        when(publicLocationService.findLocations(anyInt(), anyFloat(), anyFloat(), anyInt(), anyInt()))
                .thenReturn(Flux.just(locationDto));

        client.get()
                .uri("/locations")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$[0].id").isNotEmpty()
                .jsonPath("$[0].name").isEqualTo(locationDto.getName())
                .jsonPath("$[0].lon").isEqualTo(locationDto.getLon())
                .jsonPath("$[0].lat").isEqualTo(locationDto.getLat());
    }

    @Test
    @DisplayName("GET /locations should return LocationDto")
    void findLocationById() {
        when(publicLocationService.findLocationById(anyInt())).thenReturn(Mono.just(locationDto));

        client.get()
                .uri("/locations/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isNotEmpty()
                .jsonPath("$.name").isEqualTo(locationDto.getName())
                .jsonPath("$.lon").isEqualTo(locationDto.getLon())
                .jsonPath("$.lat").isEqualTo(locationDto.getLat());
    }

}