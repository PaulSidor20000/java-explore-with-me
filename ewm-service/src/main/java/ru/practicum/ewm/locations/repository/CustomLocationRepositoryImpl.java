package ru.practicum.ewm.locations.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.r2dbc.core.DatabaseClient;
import reactor.core.publisher.Flux;
import ru.practicum.ewm.locations.dto.LocationDto;
import ru.practicum.ewm.locations.dto.LocationParams;

@RequiredArgsConstructor
public class CustomLocationRepositoryImpl implements CustomLocationRepository {
    private final DatabaseClient client;

    @Override
    public Flux<LocationDto> findLocationsByParams(LocationParams params) {
        String query = String.format(
                "SELECT l.* FROM locations AS l %s %s",
                params.getLon() != null && params.getLat() != null && params.getRadius() != null
                        ? "AND distance(l.lat, l.lon, :lat2::real, :lon2::real) < :radius::real" : "",
                "LIMIT (:size)::bigint OFFSET (:from)::bigint;");
        DatabaseClient.GenericExecuteSpec bindings = client.sql(query);

        if (params.getLon() != null && params.getLat() != null && params.getRadius() != null) {
            bindings = bindings.bind("radius", params.getRadius())
                    .bind("lon2", params.getLon())
                    .bind("lat2", params.getLat());
        }
        return bindings
                .bind("from", params.getFrom() != null ? params.getFrom() : 0)
                .bind("size", params.getSize() != null ? params.getSize() : 10)
                .fetch().all()
                .mapNotNull(LocationDto::map);
    }
}
