package ru.practicum.statclient.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.statclient.client.RequestClient;
import ru.practicum.statclient.dto.RequestDto;
import ru.practicum.statclient.exceptions.EndBeforeStartException;

import javax.validation.Valid;
import java.time.LocalDateTime;

@Slf4j
@Validated
@RestController
@RequestMapping
@RequiredArgsConstructor
public class StatsController {
    private final RequestClient client;
    private static final String HIT_URI = "/hit";
    private static final String STATS_URI = "/stats";

    @PostMapping(HIT_URI)
    public ResponseEntity<String> saveRequest(@Valid @RequestBody RequestDto dto) throws JsonProcessingException {
        log.info("POST for new RequestDto {}", dto);
        return client.post(dto, HIT_URI);
    }

    @GetMapping(STATS_URI)
    public ResponseEntity<String> getStats(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime start,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime end,
            @RequestParam String[] uris,
            @RequestParam(required = false, value = "false") Boolean unique
    ) {
        if (end.isBefore(start)) {
            throw new EndBeforeStartException();
        }
        log.info("GET stats, start={}, end={}, uris={}, unique={}", start, end, uris, unique);
        return client.get(
                start.toString(),
                end.toString(),
                uris,
                unique,
                STATS_URI
        );
    }
}