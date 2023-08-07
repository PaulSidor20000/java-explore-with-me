package ru.practicum.ewm.compilation.repository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.practicum.ewm.compilation.dto.CompilationDto;

public interface CustomCompilationRepository {

    Flux<CompilationDto> findCompilationsByParams(Boolean pinned, Integer from, Integer size);

    Mono<CompilationDto> findCompilationDtoById(int compilationId);

    Mono<Void> deleteByCompilationId(int compilationId);

}
