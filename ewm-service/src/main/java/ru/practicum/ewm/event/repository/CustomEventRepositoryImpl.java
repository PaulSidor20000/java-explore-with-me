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

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class CustomEventRepositoryImpl implements CustomEventRepository {
    private final DatabaseClient client;
    private final R2dbcEntityTemplate template;

    private static final String EVENT_FULL_JOIN =
            "SELECT e.*," +
                    " c.name AS category_name," +
                    " u.name AS user_name," +
                    " count(r.*) FILTER ( WHERE r.status = (:status)) AS confirmed_requests" +
                    " FROM events AS e" +
                    " LEFT JOIN categories c ON c.id = e.category_id" +
                    " LEFT JOIN users u ON u.id = e.user_id" +
                    " LEFT JOIN requests r ON r.event_id = e.id";

    @Override
    public Flux<EventShortDto> getEventShortDtos(MultiValueMap<String, String> params) {
        String parameters = " WHERE e.event_state = 'PUBLISHED'" +
                " AND e.annotation LIKE concat('%',:text,'%') OR e.description LIKE concat('%',:text,'%')" +
                " AND e.category_id in (:cat)" +
                " AND e.paid = (:paid)::boolean" +
                " AND e.event_date BETWEEN (:start) AND (:end)" +
                " GROUP BY e.id, e.event_date, e.participant_limit, category_name, user_name" +
                " HAVING (e.participant_limit - (count(r.*) FILTER ( WHERE r.status = 'CONFIRMED')) <= 0) = (:available)::boolean" +
                " ORDER BY (:sort)" +
                " limit (:size) offset (:from)";

        String query = String.format("%s%s", EVENT_FULL_JOIN, parameters);

        return client.sql(query)
                .bind("status", "'CONFIRMED'")
                .bind("text", params.get("text") != null
                        ? params.get("text").stream().findFirst().orElse("")
                        : "")
                .bind("cat", params.get("categories") != null
                        ? params.get("categories").stream().map(Integer::parseInt).collect(Collectors.toList())
                        : List.of())
                .bind("paid", params.get("paid") != null
                        ? params.get("paid").stream().findFirst().orElse("false")
                        : "false")
                .bind("start", params.get("rangeStart") != null
                        ? params.get("rangeStart").stream().findFirst().orElse("current_timestamp")
                        : "current_timestamp")
                .bind("end", params.get("rangeEnd") != null
                        ? params.get("rangeEnd").stream().findFirst().orElse("current_timestamp + INTERVAL '100 years'")
                        : "current_timestamp + INTERVAL '100 years'")
                .bind("available", params.get("onlyAvailable") != null
                        ? params.get("onlyAvailable").stream().findFirst().orElse("false")
                        : "false")
                .bind("sort", params.get("sort") != null
                        ? params.get("sort").stream().findFirst().orElse("EVENT_DATE")
                        : "EVENT_DATE")
                .bind("from", params.get("from") != null
                        ? params.get("from").stream().map(Integer::parseInt).findFirst().orElse(0)
                        : 0)
                .bind("size", params.get("size") != null
                        ? params.get("size").stream().map(Integer::parseInt).findFirst().orElse(10)
                        : 10)
                .map(EventShortDto::map)
                .all().log();
    }

    @Override
    public Flux<EventFullDto> getEventFullDtos(MultiValueMap<String, String> params) {
        String parameters = " WHERE e.user_id in (:users) and" +
                " e.event_state in (:states) and" +
                " e.category_id in (:cat) and" +
                " e.event_date between (:start) and (:end)" +
                " limit (:size)" +
                " offset (:from)";
        String query = String.format("%s%s", EVENT_FULL_JOIN, parameters);

        return client.sql(query)
                .bind("users", params.get("users").stream().map(Integer::parseInt).collect(Collectors.toList()))
                .bind("states", params.get("states"))
                .bind("cat", params.get("categories").stream().map(Integer::parseInt).collect(Collectors.toList()))
                .bind("start", params.get("rangeStart").stream().findFirst().orElse("current_timestamp"))
                .bind("end", params.get("rangeEnd").stream().findFirst().orElse("current_timestamp + INTERVAL '100 years'"))
                .bind("from", params.get("from").stream().map(Integer::parseInt).findFirst().orElse(0))
                .bind("size", params.get("size").stream().map(Integer::parseInt).findFirst().orElse(10))
                .bind("status", "'CONFIRMED'")
                .map(EventFullDto::map)
                .all();
    }

    @Override
    public Mono<EventFullDto> getEventFullDto(int eventId) {
        String parameters = " WHERE e.id = (:eventId)" +
                " GROUP BY e.id, category_name, user_name";
        String query = String.format("%s%s", EVENT_FULL_JOIN, parameters);

        return client.sql(query)
//                .bind("userId", userId)
                .bind("eventId", eventId)
                .bind("status", "'CONFIRMED'")
                .map(EventFullDto::map)
                .one();
    }

    @Override
    public Flux<EventShortDto> getEventShortDtos(int userId, Pageable page) {
        String parameters = " WHERE u.id = (:userId)" +
                " GROUP BY e.id, category_name, user_name" +
                " limit (:size)" +
                " offset (:from)";
        String query = String.format("%s%s", EVENT_FULL_JOIN, parameters);

        return client.sql(query)
                .bind("userId", userId)
                .bind("from", page.getPageNumber())
                .bind("size", page.getPageSize())
                .bind("status", "'CONFIRMED'")
                .map(EventShortDto::map)
                .all();
    }

}
