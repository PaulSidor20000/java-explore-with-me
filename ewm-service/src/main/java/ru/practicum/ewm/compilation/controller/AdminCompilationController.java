package ru.practicum.ewm.compilation.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import ru.practicum.ewm.compilation.dto.CompilationDto;
import ru.practicum.ewm.compilation.dto.NewCompilationDto;
import ru.practicum.ewm.compilation.dto.UpdateCompilationRequest;
import ru.practicum.ewm.compilation.service.AdminCompilationService;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/compilations")
public class AdminCompilationController {
    private final AdminCompilationService adminCompilationService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<CompilationDto> createNewCompilation(@Valid @RequestBody NewCompilationDto dto) {
        log.info("PATCH new compilation={}", dto);

        return adminCompilationService.createNewCompilation(dto);
    }

    @PatchMapping("/{compId}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<CompilationDto> updateCompilation(@Valid @RequestBody UpdateCompilationRequest dto,
                                                  @PathVariable Integer compId
    ) {
        log.info("PATCH update compilation compId={}, compilation={}", compId, dto);

        return adminCompilationService.updateCompilation(compId, dto);
    }

    @DeleteMapping("/{compId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteCompilation(@PathVariable Integer compId) {
        log.info("DELETE compilation compId={}", compId);

        return adminCompilationService.deleteCompilation(compId);
    }
}
