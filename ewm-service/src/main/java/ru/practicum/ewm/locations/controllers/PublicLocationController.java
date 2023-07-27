package ru.practicum.ewm.locations.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.practicum.ewm.locations.dto.LocationDto;
import ru.practicum.ewm.locations.service.PublicLocationService;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/locations")
public class PublicLocationController {
    private final PublicLocationService publicLocationService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Flux<LocationDto> findLocations(@PositiveOrZero @RequestParam Integer radius,
                                           @RequestParam Float lon,
                                           @RequestParam Float lat,
                                           @RequestParam(defaultValue = "0") Integer from,
                                           @RequestParam(defaultValue = "10") Integer size
    ) {
        log.info("GET locations by: radius={}, lon={}, lat={}, from={}, size={}", radius, lon, lat, from, size);

        return publicLocationService.findLocations(radius, lon, lat, from, size);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<LocationDto> findLocationById(@PathVariable Integer id) {
        log.info("GET location by id={}", id);

        return publicLocationService.findLocationById(id);
    }

}
