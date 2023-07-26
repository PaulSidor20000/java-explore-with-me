package ru.practicum.ewm.event.repository;


import lombok.RequiredArgsConstructor;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.r2dbc.core.Parameter;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.practicum.ewm.event.dto.EventFullDto;
import ru.practicum.ewm.event.dto.EventParams;
import ru.practicum.ewm.event.dto.EventShortDto;

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
    public Flux<EventFullDto> getAdminEventFullDtos(EventParams params) {
        String query = String.format("%s%s%s%s%s%s%s", EVENT_FULL_JOIN,
                params.getUsers() != null ? " WHERE e.user_id IN (:users)" : " WHERE e.user_id IS NOT NULL",
                params.getCategories() != null ? " AND e.category_id IN (:cat)" : " AND e.category_id IS NOT NULL",
                params.getStates() != null ? " AND e.event_state IN (:states)" : " AND e.event_state IS NOT NULL",
                params.getRangeStart() != null && params.getRangeEnd() != null
                        ? " AND e.event_date between (:start) AND (:end)"
                        : " AND e.event_date::timestamp > current_timestamp",
                params.getLon() != null && params.getLat() != null && params.getRadius() != null
                        ? " AND distance(l.lat, l.lon, :lat2::real, :lon2::real) < :radius::real"
                        : "",
                " GROUP BY e.event_id, l.location_id, category_name, user_name" +
                        " ORDER BY e.event_id DESC" +
                        " LIMIT (:size)::bigint" +
                        " OFFSET (:from)::bigint");
        DatabaseClient.GenericExecuteSpec bindings = client.sql(query);

        if (params.getLon() != null && params.getLat() != null && params.getRadius() != null) {
            bindings = bindings.bind("radius", params.getRadius())
                    .bind("lon2", params.getLon())
                    .bind("lat2", params.getLat());
        }
        if (params.getUsers() != null) {
            bindings = bindings.bind("users", params.getUsers());
        }
        if (params.getCategories() != null) {
            bindings = bindings.bind("cat", params.getCategories());
        }
        if (params.getStates() != null) {
            bindings = bindings.bind("states", params.getStates());
        }
        if (params.getRangeStart() != null) {
            bindings = bindings.bind("start", params.getRangeStart());
        }
        if (params.getRangeEnd() != null) {
            bindings = bindings.bind("end", params.getRangeEnd());
        }
        return bindings
                .bind("from", params.getFrom() != null ? params.getFrom() : 0)
                .bind("size", params.getSize() != null ? params.getSize() : 10)
                .fetch().all()
                .mapNotNull(EventFullDto::map);
    }

    @Override
    public Flux<EventShortDto> getPublicEventShortDtos(EventParams params) {
        String query = String.format("%s%s%s%s%s%s", EVENT_SHORT_JOIN,
                " WHERE e.event_state = 'PUBLISHED'" +
                        " AND e.annotation LIKE concat('%',:text,'%')",
                params.getCategories() != null ? " AND e.category_id IN (:cat)" : " AND e.category_id IS NOT NULL",
                params.getPaid() != null ? " AND e.paid = (:paid)::boolean" : " AND e.paid IS NOT NULL",
                params.getRangeStart() != null && params.getRangeEnd() != null
                        ? " AND e.event_date between (:start) AND (:end)"
                        : " AND e.event_date::timestamp > current_timestamp",
                " GROUP BY e.event_id, e.event_date, e.participant_limit, category_name, user_name" +
                        " HAVING (e.participant_limit - (count(r.*) FILTER ( WHERE request_status = 'CONFIRMED')) <= 0) = (:available)::boolean" +
                        " ORDER BY (:sort) DESC" +
                        " LIMIT (:size)::bigint" +
                        " OFFSET (:from)::bigint");
        DatabaseClient.GenericExecuteSpec bindings = client.sql(query);

        if (params.getCategories() != null) {
            bindings = bindings.bind("cat", params.getCategories());
        }
        if (params.getPaid() != null) {
            bindings = bindings.bind("paid", params.getPaid());
        }
        if (params.getRangeStart() != null) {
            bindings = bindings.bind("start", params.getRangeStart());
        }
        if (params.getRangeEnd() != null) {
            bindings = bindings.bind("end", params.getRangeEnd());
        }
        return bindings
                .bind("available", params.getOnlyAvailable() != null ? params.getOnlyAvailable() : "false")
                .bind("text", Parameter.fromOrEmpty(params.getText(), String.class))
                .bind("sort", params.getSort() != null ? params.getSort() : "EVENT_DATE")
                .bind("from", params.getFrom() != null ? params.getFrom() : 0)
                .bind("size", params.getSize() != null ? params.getSize() : 10)
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
    public Flux<EventShortDto> getPrivateEventShortDtos(int userId, Integer from, Integer size) {
        String query = EVENT_SHORT_JOIN +
                " WHERE u.user_id = (:userId)" +
                " GROUP BY e.event_id, category_name, user_name" +
                " LIMIT (:size) OFFSET (:from)";

        return client.sql(query)
                .bind("userId", userId)
                .bind("from", from)
                .bind("size", size)
                .fetch().all()
                .mapNotNull(EventShortDto::map);
    }

}
