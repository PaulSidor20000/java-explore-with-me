package ru.practicum.ewm.event.repository;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.util.MultiValueMap;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.practicum.ewm.event.dto.EventFullDto;
import ru.practicum.ewm.event.dto.EventShortDto;

import java.util.stream.Collectors;

@RequiredArgsConstructor
public class CustomEventRepositoryImpl implements CustomEventRepository {
    private final DatabaseClient client;
    private final R2dbcEntityTemplate template;

    private static final String EVENT_FULL_JOIN =
            "select e.*," +
                    " c.name as category_name," +
                    " u.name as user_name" +

                    " r.id as confirmed_requests" +
                    // TODO confirmed_requests

                    " from events as e" +
                    " join categories c on c.id = e.category_id" +
                    " join users u on u.id = e.user_id" +
                    " join requests r on r.id = e.event_id";

    @Override
    public Mono<EventFullDto> getEventFullDto(int eventId) {
        String where = "WHERE e.id = :eventId" +
                " and count(r.status = :status)";
        String query = String.format("%s %s", EVENT_FULL_JOIN, where);

        return client.sql(query)
//                .bind("userId", userId)
                .bind("eventId", eventId)
                .bind("status", "'CONFIRMED'")
                .map(EventFullDto::map)
                .one();
    }

    @Override
    public Flux<EventFullDto> getEventFullDtos(MultiValueMap<String, String> params) {
        String parameters = "e.user_id in (:users) and" +
                " e.event_state in (:states) and" +
                " e.category_id in (:cat)" +
//                " u.id in (:start) and" +
//                " u.id in (:end) and" +
                " limit (:size)" +
                " offset (:from)";
        String query = String.format("%s WHERE %s", EVENT_FULL_JOIN, parameters);

        return client.sql(query)
                .bind("users", params.get("users").stream().map(Integer::parseInt).collect(Collectors.toList()))
                .bind("states", params.get("states"))
                .bind("cat", params.get("categories").stream().map(Integer::parseInt).collect(Collectors.toList()))
//                .bind("start", params.get("rangeStart"))
//                .bind("end", params.get("rangeEnd"))
                .bind("from", params.get("from").stream().map(Integer::parseInt).findFirst().orElse(0))
                .bind("size", params.get("size").stream().map(Integer::parseInt).findFirst().orElse(10))
                .map(EventFullDto::map)
                .all();
    }

    @Override
    public Flux<EventShortDto> getEventShortDtos(int userId, Pageable page) {
        String query = String.format("%s WHERE u.id = :userId limit :size offset :from", EVENT_FULL_JOIN);

        return client.sql(query)
                .bind("userId", userId)
                .bind("from", page.getPageNumber())
                .bind("size", page.getPageSize())
                .map(EventShortDto::map)
                .all();
    }

}
