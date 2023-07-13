package ru.practicum.ewm.compilation.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.practicum.ewm.compilation.entity.Compilation;

@Repository
@RequiredArgsConstructor
public class EventCompilationsRepositoryImpl implements EventCompilationsRepository {
    private final DatabaseClient client;

    @Override
    public Mono<Compilation> saveCompilationOfEvents(Compilation compilation) {
        String query = "INSERT INTO event_compilations (event_id, compilation_id) VALUES (:events, :compId)";

        if (compilation != null && compilation.getEvents() != null) {
            return Flux.fromIterable(compilation.getEvents())
                    .flatMap(id -> client.sql(query)
                            .bind("events", id)
                            .bind("compId", compilation.getId())
                            .fetch().rowsUpdated())
                    .then(Mono.just(compilation));
        }
        return Mono.justOrEmpty(compilation);
    }

    @Override
    public Mono<Compilation> updateCompilationOfEvents(Compilation compilation) {
        return deleteCompilationOfEvents(compilation)
                .flatMap(this::saveCompilationOfEvents);
    }

    @Override
    public Mono<Compilation> deleteCompilationOfEvents(Compilation compilation) {
        String query = "DELETE FROM event_compilations WHERE compilation_id IN (:compId)";

        return client.sql(query)
                .bind("compId", compilation.getId())
                .fetch().rowsUpdated()
                .thenReturn(compilation);
    }

}
