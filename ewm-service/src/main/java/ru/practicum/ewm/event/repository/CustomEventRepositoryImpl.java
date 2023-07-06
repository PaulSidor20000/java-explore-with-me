package ru.practicum.ewm.event.repository;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.r2dbc.core.Parameter;
import org.springframework.util.MultiValueMap;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.practicum.ewm.event.dto.EventFullDto;
import ru.practicum.ewm.event.dto.EventShortDto;

import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

import static java.lang.Integer.parseInt;

@RequiredArgsConstructor
public class CustomEventRepositoryImpl implements CustomEventRepository {
    private final DatabaseClient client;
    private final R2dbcEntityTemplate template;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private static final String EVENT_FULL_JOIN =
            "SELECT e.*," +
                    " c.name AS category_name," +
                    " u.name AS user_name," +
                    " count(r.*) FILTER ( WHERE r.status IN (:status)) AS confirmed_requests" +
                    " FROM events AS e" +
                    " LEFT JOIN categories c ON c.id = e.category_id" +
                    " LEFT JOIN users u ON u.id = e.user_id" +
                    " LEFT JOIN requests r ON r.event_id = e.id";

    @Override
    public Flux<EventFullDto> getAdminEventFullDtos(MultiValueMap<String, String> params) {
        String parameters = " WHERE " +
                " e.user_id IN (:users) OR e.user_id IS NOT NULL" +
                " AND e.event_state IN (:states) OR e.event_state IS NOT NULL" +
                " AND e.category_id IN (:cat) OR e.category_id IS NOT NULL" +
                " AND e.event_date between (:start) AND (:end) OR e.event_date::timestamp > current_timestamp" +
                " GROUP BY e.id, category_name, user_name " +
                " ORDER BY e.id DESC" +
                " LIMIT (:size) OFFSET (:from)";
        String query = String.format("%s%s", EVENT_FULL_JOIN, parameters);

        return client.sql(query)
                .bind("users", params.get("users") != null
                        ? params.get("users").stream().map(Integer::parseInt).collect(Collectors.toList())
                        : Parameter.empty(Integer.class))
                .bind("cat", params.get("categories") != null
                        ? params.get("categories").stream().map(Integer::parseInt).collect(Collectors.toList())
                        : Parameter.empty(Integer.class))
                .bind("states", Parameter.fromOrEmpty(params.get("states"), String.class))
                .bind("start", Parameter.fromOrEmpty(params.get("rangeStart"), String.class))
                .bind("end", Parameter.fromOrEmpty(params.get("rangeEnd"), String.class))
                .bind("from", params.get("from") != null ? parseInt(params.get("from").get(0)) : 0)
                .bind("size", params.get("size") != null ? parseInt(params.get("size").get(0)) : 10)
                .bind("status", "CONFIRMED")
                .map(EventFullDto::map)
                .all().log();
    }

    @Override
    public Flux<EventShortDto> getPublicEventShortDtos(MultiValueMap<String, String> params) {
        String parameters = " WHERE " +
                " e.event_state = 'PUBLISHED'" +
                " AND e.annotation LIKE concat('%',:text,'%') OR e.description LIKE concat('%',:text,'%')" +
                " AND e.category_id IN (:cat) OR e.category_id IS NOT NULL" +
                " AND e.paid = (:paid)::boolean OR e.paid IS NOT NULL" +
                " AND e.event_date between (:start) AND (:end) OR e.event_date::timestamp > current_timestamp" +
                " GROUP BY e.id, e.event_date, e.participant_limit, category_name, user_name" +
                " HAVING (e.participant_limit - (count(r.*) FILTER ( WHERE r.status = 'CONFIRMED')) <= 0) = (:available)::boolean" +
                " ORDER BY (:sort) DESC" +
                " limit (:size) offset (:from)";

        String query = String.format("%s%s", EVENT_FULL_JOIN, parameters);

//                .bind("paid", params.get("paid") != null
//                        ? params.get("paid").stream().findFirst().orElse("false")
//                        : "false")

        return client.sql(query)
                .bind("cat", params.get("categories") != null
                        ? params.get("categories").stream().map(Integer::parseInt).collect(Collectors.toList())
                        : Parameter.empty(Integer.class))
                .bind("available", params.get("onlyAvailable") != null
                        ? params.get("onlyAvailable").stream().findFirst().orElse("false")
                        : "false")
                .bind("text", Parameter.fromOrEmpty(params.get("text"), String.class))
                .bind("paid", Parameter.fromOrEmpty(params.get("paid"), Boolean.class))
                .bind("start", Parameter.fromOrEmpty(params.get("rangeStart"), String.class))
                .bind("end", Parameter.fromOrEmpty(params.get("rangeEnd"), String.class))
                .bind("sort", params.get("sort") != null ? params.get("sort") : "EVENT_DATE")
                .bind("from", params.get("from") != null ? parseInt(params.get("from").get(0)) : 0)
                .bind("size", params.get("size") != null ? parseInt(params.get("size").get(0)) : 10)
                .bind("status", "CONFIRMED")
                .map(EventShortDto::map)
                .all().log();
    }

    @Override
    public Mono<EventFullDto> getEventFullDto(int eventId) {
        String parameters = "  WHERE" +
                " e.id = (:eventId)" +
                " GROUP BY e.id, category_name, user_name ";
        String query = String.format("%s%s", EVENT_FULL_JOIN, parameters);

        return client.sql(query)
//                .bind("userId", userId)
                .bind("eventId", eventId)
                .bind("status", "CONFIRMED")
                .map(EventFullDto::map)
                .one();
    }

    @Override
    public Mono<EventFullDto> getPublicEventFullDto(int eventId) {
        String parameters = " WHERE" +
                " e.event_state = 'PUBLISHED'" +
                " AND e.id = (:eventId)" +
                " GROUP BY e.id, category_name, user_name";
        String query = String.format("%s%s", EVENT_FULL_JOIN, parameters);

        return client.sql(query)
//                .bind("userId", userId)
                .bind("eventId", eventId)
                .bind("status", "CONFIRMED")
                .map(EventFullDto::map)
                .one();
    }

    @Override
    public Flux<EventShortDto> getPrivateEventShortDtos(int userId, Pageable page) {
        String parameters = " WHERE" +
                " u.id = (:userId)" +
                " GROUP BY e.id, category_name, user_name" +
                " limit (:size)" +
                " offset (:from)";
        String query = String.format("%s%s", EVENT_FULL_JOIN, parameters);

        return client.sql(query)
                .bind("userId", userId)
                .bind("from", page.getPageNumber())
                .bind("size", page.getPageSize())
                .bind("status", "CONFIRMED")
                .map(EventShortDto::map)
                .all();
    }

}
