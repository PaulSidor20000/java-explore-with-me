package ru.practicum.ewm.compilation.repository;

import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import ru.practicum.ewm.compilation.entity.Compilation;

@Repository
public interface EventCompilationsRepository {

    Mono<Compilation> saveCompilationOfEvents(Compilation compilation);

    Mono<Compilation> updateCompilationOfEvents(Compilation compilation);

    Mono<Compilation> deleteCompilationOfEvents(Compilation compilation);

}
