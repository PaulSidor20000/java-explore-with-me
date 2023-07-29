package ru.practicum.ewm.locations.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.practicum.ewm.locations.dto.LocationDto;
import ru.practicum.ewm.locations.dto.LocationParams;
import ru.practicum.ewm.locations.service.PublicLocationService;

import javax.validation.Valid;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/locations")
public class PublicLocationController {
    private final PublicLocationService publicLocationService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Flux<LocationDto> findLocations(@Valid @ModelAttribute LocationParams params) {
        log.info("GET locations by params={}", params);

        return publicLocationService.findLocations(params);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<LocationDto> findLocationById(@PathVariable Integer id) {
        log.info("GET location by id={}", id);

        return publicLocationService.findLocationById(id);
    }

}
