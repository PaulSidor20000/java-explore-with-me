package ru.practicum.ewm.compilation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import ru.practicum.ewm.compilation.dto.CompilationDto;
import ru.practicum.ewm.compilation.dto.CompilationMapper;
import ru.practicum.ewm.compilation.dto.NewCompilationDto;
import ru.practicum.ewm.compilation.dto.UpdateCompilationRequest;
import ru.practicum.ewm.compilation.entity.Compilation;
import ru.practicum.ewm.compilation.repository.CompilationRepository;
import ru.practicum.ewm.compilation.repository.EventCompilationsRepository;

@Service
@RequiredArgsConstructor
public class AdminCompilationServiceImpl implements AdminCompilationService {
    private final EventCompilationsRepository eventCompilationsRepository;
    private final CompilationRepository compilationRepository;
    private final CompilationMapper compilationMapper;

    @Override
    @Transactional
    public Mono<CompilationDto> createNewCompilation(NewCompilationDto dto) {
        return Mono.just(compilationMapper.map(dto))
                .flatMap(compilationRepository::save)
                .flatMap(eventCompilationsRepository::saveCompilationOfEvents)
                .map(Compilation::getId)
                .flatMap(compilationRepository::findCompilationDtoById);
    }

    @Override
    public Mono<CompilationDto> updateCompilation(int compilationId, UpdateCompilationRequest dto) {
        return compilationRepository.findById(compilationId)
                .map(compilation -> compilationMapper.merge(compilation, dto))
                .flatMap(compilationRepository::save)
                .flatMap(eventCompilationsRepository::updateCompilationOfEvents)
                .map(Compilation::getId)
                .flatMap(compilationRepository::findCompilationDtoById);
    }

    @Override
    public Mono<Void> deleteCompilation(int compilationId) {
        return compilationRepository.deleteByCompilationId(compilationId);
    }
}
