package ru.practicum.ewm.compilation.service;

import reactor.core.publisher.Mono;
import ru.practicum.ewm.compilation.dto.CompilationDto;
import ru.practicum.ewm.compilation.dto.NewCompilationDto;
import ru.practicum.ewm.compilation.dto.UpdateCompilationRequest;


public interface AdminCompilationService {
    Mono<CompilationDto> createNewCompilation(NewCompilationDto dto);

    Mono<CompilationDto> updateCompilation(int compilationId, UpdateCompilationRequest dto);

    Mono<Void> deleteCompilation(int compilationId);
}
