package ru.practicum.ewm.compilation.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.practicum.ewm.compilation.dto.CompilationDto;
import ru.practicum.ewm.compilation.service.PublicCompilationService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/compilations")
public class PublicCompilationController {
    private final PublicCompilationService publicCompilationService;

    @GetMapping("{compId}")
    public Mono<CompilationDto> findCompilationById(@PathVariable Integer compId) {
        log.info("GET compilation by compId={}", compId);

        return publicCompilationService.findCompilationById(compId);
    }

    @GetMapping
    public Flux<CompilationDto> findCompilations(@RequestParam(required = false) Boolean pinned,
                                                 @RequestParam(defaultValue = "0") Integer from,
                                                 @RequestParam(defaultValue = "10") Integer size
    ) {
        log.info("GET compilation by params pinned={}, from={}, size={}", pinned, from, size);

        return publicCompilationService.findCompilations(pinned, from, size);
    }

}
