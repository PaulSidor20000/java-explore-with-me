package ru.practicum.ewm.locations.controllers;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import ru.practicum.ewm.locations.dto.LocationDto;
import ru.practicum.ewm.locations.dto.NewLocationDto;
import ru.practicum.ewm.locations.service.AdminLocationService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@WebFluxTest(controllers = AdminLocationController.class)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class AdminLocationControllerTest {
    private final WebTestClient client;
    @MockBean
    private AdminLocationService adminLocationService;
    private final LocationDto locationDto = LocationDto.builder().id(1).name("Москва").lon(100F).lat(150F).build();
    private final NewLocationDto newLocationDto = NewLocationDto.builder().name("Москва").lon(100F).lat(150F).build();

    @Test
    @DisplayName("POST /admin/locations should return LocationDto")
    void createLocation() {
        when(adminLocationService.createLocation(any())).thenReturn(Mono.just(locationDto));

        client.post()
                .uri("/admin/locations")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(newLocationDto), NewLocationDto.class)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.id").isNotEmpty()
                .jsonPath("$.name").isEqualTo(locationDto.getName())
                .jsonPath("$.lon").isEqualTo(locationDto.getLon())
                .jsonPath("$.lat").isEqualTo(locationDto.getLat());
    }

    @Test
    @DisplayName("DELETE /admin/locations/1 should return 204 status")
    void deleteLocation() {
        when(adminLocationService.deleteLocation(anyInt())).thenReturn(Mono.empty());

        client.delete()
                .uri("/admin/locations/1")
                .exchange()
                .expectStatus().isNoContent()
                .expectBody(String.class);
    }

}