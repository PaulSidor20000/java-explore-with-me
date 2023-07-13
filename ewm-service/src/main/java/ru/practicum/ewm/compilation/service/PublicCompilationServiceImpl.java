package ru.practicum.ewm.compilation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.practicum.ewm.compilation.dto.CompilationDto;
import ru.practicum.ewm.compilation.repository.CompilationRepository;

@Service
@RequiredArgsConstructor
public class PublicCompilationServiceImpl implements PublicCompilationService {
    private final CompilationRepository compilationRepository;

    @Override
    public Mono<CompilationDto> findCompilationById(int compilationId) {
        return compilationRepository.findCompilationDtoById(compilationId);
    }

    @Override
    public Flux<CompilationDto> findCompilations(MultiValueMap<String, String> params) {
        return compilationRepository.findCompilationsByParams(params);
    }

}
