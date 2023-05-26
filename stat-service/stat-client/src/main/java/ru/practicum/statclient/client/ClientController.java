package ru.practicum.statclient.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.practicum.statdto.dto.RequestDto;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Optional;

@Slf4j
@Validated
@RestController
@RequestMapping
@RequiredArgsConstructor
public class ClientController {
    private final ClientService client;
    private static final String HIT_URI = "/hit";
    private static final String STATS_URI = "/stats";

    @PostMapping(HIT_URI)
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<String> saveRequest(@Valid @RequestBody RequestDto dto) throws JsonProcessingException {
        log.info("POST for new RequestDto {}", dto);
        return client.post(dto, HIT_URI);
    }

    @GetMapping(STATS_URI)
    public Flux<String> getStats(@RequestParam String start,
                                 @RequestParam String end,
                                 @RequestParam(required = false) Collection<String> uris,
                                 @RequestParam(required = false, defaultValue = "false") boolean unique
    ) {
        log.info("GET stats, start={}, end={}, uris={}, unique={}", start, end, uris, unique);
        return client.get(start, end, Optional.ofNullable(uris), unique, STATS_URI);
    }

}