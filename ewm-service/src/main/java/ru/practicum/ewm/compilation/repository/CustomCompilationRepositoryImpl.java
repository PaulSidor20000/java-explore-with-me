package ru.practicum.ewm.compilation.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.r2dbc.core.DatabaseClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.practicum.ewm.compilation.dto.CompilationDto;

@RequiredArgsConstructor
public class CustomCompilationRepositoryImpl implements CustomCompilationRepository {
    private final DatabaseClient client;
    private static final String SELECT_QUERY =
            "SELECT comp.*, e.*, category_name, user_name," +
                    " count(r.*) FILTER (WHERE request_status = 'CONFIRMED') AS confirmed_requests" +
                    " FROM compilations AS comp" +
                    " LEFT JOIN event_compilations ec on comp.compilation_id = ec.compilation_id" +
                    " LEFT JOIN events e on e.event_id = ec.event_id" +
                    " LEFT JOIN users u ON u.user_id = e.user_id" +
                    " LEFT JOIN categories c ON c.category_id = e.category_id" +
                    " LEFT JOIN requests r ON r.event_id = e.event_id";

    @Override
    public Flux<CompilationDto> findCompilationsByParams(Boolean pinned, Integer from, Integer size) {
        String query = String.format("%s%s%s", SELECT_QUERY,
                pinned != null
                        ? " WHERE comp.compilation_pinned = (:pinned)::boolean"
                        : " WHERE comp.compilation_pinned IS NOT NULL",
                " GROUP BY e.event_id, comp.compilation_id, category_name, user_name" +
                        " LIMIT (:size)::bigint OFFSET (:from)::bigint");
        DatabaseClient.GenericExecuteSpec bindings = client.sql(query);

        if (pinned != null) {
            bindings = bindings.bind("pinned", pinned);
        }

        return bindings
                .bind("from", from)
                .bind("size", size)
                .fetch().all()
                .bufferUntilChanged(result -> result.get("compilation_id"))
                .map(CompilationDto::map);
    }

    @Override
    public Mono<CompilationDto> findCompilationDtoById(int compilationId) {
        String query = SELECT_QUERY +
                " WHERE comp.compilation_id IN (:compId)" +
                " GROUP BY e.event_id, comp.compilation_id, category_name, user_name;";

        return client.sql(query)
                .bind("compId", compilationId)
                .fetch().all()
                .bufferUntilChanged(result -> result.get("compilation_id"))
                .map(CompilationDto::map)
                .singleOrEmpty();
    }

    @Override
    public Mono<Void> deleteByCompilationId(int compilationId) {
        return client.sql("DELETE FROM compilations WHERE compilation_id IN (:compId)")
                .bind("compId", compilationId)
                .then();
    }

}
