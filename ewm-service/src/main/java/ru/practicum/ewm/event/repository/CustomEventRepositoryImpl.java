package ru.practicum.ewm.event.repository;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.r2dbc.core.Parameter;
import org.springframework.util.MultiValueMap;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.practicum.ewm.event.dto.EventFullDto;
import ru.practicum.ewm.event.dto.EventShortDto;

import java.util.stream.Collectors;

@RequiredArgsConstructor
public class CustomEventRepositoryImpl implements CustomEventRepository {
    private final DatabaseClient client;
    private static final String EVENT_SHORT_JOIN =
            "SELECT e.*, category_name, user_name," +
                    " count(r.*) FILTER (WHERE request_status = 'CONFIRMED') AS confirmed_requests" +
                    " FROM events AS e" +
                    " LEFT JOIN categories c ON c.category_id = e.category_id" +
                    " LEFT JOIN users u ON u.user_id = e.user_id" +
                    " LEFT JOIN requests r ON r.event_id = e.event_id";
    private static final String EVENT_FULL_JOIN =
            "SELECT e.*, l.*, category_name, user_name," +
                    " count(r.*) FILTER (WHERE request_status = 'CONFIRMED') AS confirmed_requests" +
                    " FROM events AS e" +
                    " LEFT JOIN categories c ON c.category_id = e.category_id" +
                    " LEFT JOIN users u ON u.user_id = e.user_id" +
                    " LEFT JOIN locations l ON e.location_id = l.location_id" +
                    " LEFT JOIN requests r ON r.event_id = e.event_id";

    @Override
    public Flux<EventFullDto> getAdminEventFullDtos(MultiValueMap<String, String> params) {
        String query = String.format("%s%s%s%s%s%s", EVENT_FULL_JOIN,
                params.get("users") != null ? " WHERE e.user_id IN (:users)" : " WHERE e.user_id IS NOT NULL",
                params.get("categories") != null ? " AND e.category_id IN (:cat)" : " AND e.category_id IS NOT NULL",
                params.get("states") != null ? " AND e.event_state IN (:states)" : " AND e.event_state IS NOT NULL",
                params.get("rangeStart") != null && params.get("rangeEnd") != null
                        ? " AND e.event_date between (:start) AND (:end)"
                        : " AND e.event_date::timestamp > current_timestamp",
                " GROUP BY e.event_id, l.location_id, category_name, user_name ORDER BY e.event_id DESC LIMIT (:size)::bigint OFFSET (:from)::bigint");
        DatabaseClient.GenericExecuteSpec bindings = client.sql(query);

        if (params.get("users") != null) {
            bindings = bindings.bind("users", params.get("users").stream()
                    .map(Integer::parseInt).collect(Collectors.toList()));
        }
        if (params.get("categories") != null) {
            bindings = bindings.bind("cat", params.get("categories").stream()
                    .map(Integer::parseInt).collect(Collectors.toList()));
        }
        if (params.get("states") != null) {
            bindings = bindings.bind("states", params.get("states"));
        }
        if (params.get("rangeStart") != null) {
            bindings = bindings.bind("start", params.get("rangeStart"));
        }
        if (params.get("rangeEnd") != null) {
            bindings = bindings.bind("end", params.get("rangeEnd"));
        }
        return bindings
                .bind("from", params.get("from") != null ? params.get("from") : 0)
                .bind("size", params.get("size") != null ? params.get("size") : 10)
                .fetch().all()
                .mapNotNull(EventFullDto::map);
    }

    @Override
    public Flux<EventShortDto> getPublicEventShortDtos(MultiValueMap<String, String> params) {
        String query = String.format("%s%s%s%s%s%s", EVENT_SHORT_JOIN,
                " WHERE e.event_state = 'PUBLISHED'" +
                        " AND e.annotation LIKE concat('%',:text,'%')",
                params.get("categories") != null ? " AND e.category_id IN (:cat)" : " AND e.category_id IS NOT NULL",
                params.get("paid") != null ? " AND e.paid = (:paid)::boolean" : " AND e.paid IS NOT NULL",
                params.get("rangeStart") != null && params.get("rangeEnd") != null
                        ? " AND e.event_date between (:start) AND (:end)"
                        : " AND e.event_date::timestamp > current_timestamp",
                " GROUP BY e.event_id, e.event_date, e.participant_limit, category_name, user_name" +
                        " HAVING (e.participant_limit - (count(r.*) FILTER ( WHERE request_status = 'CONFIRMED')) <= 0) = (:available)::boolean" +
                        " ORDER BY (:sort) DESC LIMIT (:size)::bigint OFFSET (:from)::bigint");
        DatabaseClient.GenericExecuteSpec bindings = client.sql(query);

        if (params.get("categories") != null) {
            bindings = bindings.bind("cat", params.get("categories").stream()
                    .map(Integer::parseInt).collect(Collectors.toList()));
        }
        if (params.get("paid") != null) {
            bindings = bindings.bind("paid", params.get("paid"));
        }
        if (params.get("rangeStart") != null) {
            bindings = bindings.bind("start", params.get("rangeStart"));
        }
        if (params.get("rangeEnd") != null) {
            bindings = bindings.bind("end", params.get("rangeEnd"));
        }
        return bindings
                .bind("available", params.get("onlyAvailable") != null ? params.get("onlyAvailable") : "false")
                .bind("text", Parameter.fromOrEmpty(params.get("text"), String.class))
                .bind("sort", params.get("sort") != null ? params.get("sort") : "EVENT_DATE")
                .bind("from", params.get("from") != null ? params.get("from") : 0)
                .bind("size", params.get("size") != null ? params.get("size") : 10)
                .fetch().all()
                .mapNotNull(EventShortDto::map);
    }

    @Override
    public Mono<EventFullDto> getEventFullDto(int eventId) {
        String query = EVENT_FULL_JOIN +
                " WHERE e.event_id = (:eventId)" +
                " GROUP BY e.event_id, l.location_id, category_name, user_name ";

        return client.sql(query)
                .bind("eventId", eventId)
                .fetch().one()
                .mapNotNull(EventFullDto::map);
    }

    @Override
    public Mono<EventFullDto> getPublicEventFullDto(int eventId) {
        String query = EVENT_FULL_JOIN +
                " WHERE e.event_state = 'PUBLISHED'" +
                " AND e.event_id = (:eventId)" +
                " GROUP BY e.event_id, l.location_id, category_name, user_name";

        return client.sql(query)
                .bind("eventId", eventId)
                .fetch().one()
                .mapNotNull(EventFullDto::map);
    }

    @Override
    public Flux<EventShortDto> getPrivateEventShortDtos(int userId, Pageable page) {
        String query = EVENT_SHORT_JOIN +
                " WHERE u.user_id = (:userId)" +
                " GROUP BY e.event_id, category_name, user_name" +
                " LIMIT (:size) OFFSET (:from)";

        return client.sql(query)
                .bind("userId", userId)
                .bind("from", page.getPageNumber())
                .bind("size", page.getPageSize())
                .fetch().all()
                .mapNotNull(EventShortDto::map);
    }

}
