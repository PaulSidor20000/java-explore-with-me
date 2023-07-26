package ru.practicum.ewm.compilation.service;

import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.practicum.ewm.compilation.dto.CompilationDto;

@Service
public interface PublicCompilationService {

    Mono<CompilationDto> findCompilationById(int compilationId);

    Flux<CompilationDto> findCompilations(Boolean pinned, Integer from, Integer size);

}
