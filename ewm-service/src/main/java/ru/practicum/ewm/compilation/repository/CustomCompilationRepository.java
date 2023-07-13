package ru.practicum.ewm.compilation.repository;

import org.springframework.util.MultiValueMap;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.practicum.ewm.compilation.dto.CompilationDto;

public interface CustomCompilationRepository {

    Flux<CompilationDto> findCompilationsByParams(MultiValueMap<String, String> params);

    Mono<CompilationDto> findCompilationDtoById(int compilationId);

    Mono<Void> deleteByCompilationId(int compilationId);

}
