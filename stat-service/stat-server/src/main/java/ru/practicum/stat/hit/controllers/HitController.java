package ru.practicum.stat.hit.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.practicum.stat.hit.service.HitService;
import ru.practicum.statdto.dto.RequestDto;
import ru.practicum.statdto.dto.ViewStats;

import java.time.LocalDateTime;
import java.util.Collection;

@Slf4j
@RestController
@RequestMapping
@RequiredArgsConstructor
public class HitController {
    private final HitService statService;
    private static final String HIT_URI = "/hit";
    private static final String STATS_URI = "/stats";

    @PostMapping(HIT_URI)
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<String> saveRequest(@RequestBody RequestDto dto) {
        log.info("POST for new RequestDto {}", dto);
        return statService.saveRequest(dto);
    }

    @GetMapping(STATS_URI)
    public Flux<ViewStats> getStats(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime start,
                                    @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime end,
                                    @RequestParam(required = false) Collection<String> uris,
                                    @RequestParam(required = false, defaultValue = "false") boolean unique
    ) {
        log.info("GET stats, start={}, end={}, uris={}, unique={}", start, end, uris, unique);
        if (start.isAfter(end)) {
            throw new IllegalArgumentException("Start time must be before end time");
        }
        return statService.getStats(start, end, uris, unique);
    }

}
