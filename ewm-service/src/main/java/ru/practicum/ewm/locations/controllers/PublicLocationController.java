package ru.practicum.ewm.locations.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.practicum.ewm.locations.dto.LocationDto;
import ru.practicum.ewm.locations.service.PublicLocationService;

import javax.validation.constraints.NotNull;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/locations")
public class PublicLocationController {
    private final PublicLocationService publicLocationService;

    @PostMapping
    public Flux<LocationDto> findLocations(@NotNull @RequestParam Integer radius,
                                           @NotNull @RequestParam Float lon,
                                           @NotNull @RequestParam Float lat,
                                           @RequestParam(defaultValue = "0") Integer from,
                                           @RequestParam(defaultValue = "10") Integer size
    ) {
        log.info("GET locations by: radius={}, lon={}, lat={}, from={}, size={}", radius, lon, lat, from, size);
        return publicLocationService.findLocations(radius, lon, lat, from, size);
    }

    @GetMapping("/{id}")
    public Mono<LocationDto> findLocationById(@PathVariable Integer id) {
        log.info("GET location by id={}", id);
        return publicLocationService.findLocationById(id);
    }

}
