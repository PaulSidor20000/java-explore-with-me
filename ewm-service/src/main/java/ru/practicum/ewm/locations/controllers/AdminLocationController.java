package ru.practicum.ewm.locations.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import ru.practicum.ewm.locations.dto.LocationDto;
import ru.practicum.ewm.locations.dto.NewLocationDto;
import ru.practicum.ewm.locations.service.AdminLocationService;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/locations")
public class AdminLocationController {
    private final AdminLocationService adminLocationService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<LocationDto> createLocation(@Valid @RequestBody NewLocationDto dto) {
        log.info("POST create location={}", dto);

        return adminLocationService.createLocation(dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteLocation(@PathVariable Integer id) {
        log.info("DELETE location id={}", id);

        return adminLocationService.deleteLocation(id);
    }

}
