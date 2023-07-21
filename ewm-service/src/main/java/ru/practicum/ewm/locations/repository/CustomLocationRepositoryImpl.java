package ru.practicum.ewm.locations.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.util.MultiValueMap;
import reactor.core.publisher.Flux;
import ru.practicum.ewm.locations.dto.LocationDto;

@RequiredArgsConstructor
public class CustomLocationRepositoryImpl implements CustomLocationRepository {
    private final DatabaseClient client;
    private static final String SELECT_QUERY =
            "SELECT l.*" +
                    " FROM locations AS l";

    @Override
    public Flux<LocationDto> findLocationsByParams(MultiValueMap<String, String> params) {
        String query = SELECT_QUERY +
                " WHERE distance(l.lat, l.lon, :lat2::real, :lon2::real) < :radius::real" +
                " LIMIT (:size)::bigint OFFSET (:from)::bigint;";

        return client.sql(query)
                .bind("radius", params.get("radius"))
                .bind("lon2", params.get("lon"))
                .bind("lat2", params.get("lat"))
                .bind("from", params.get("from") != null ? params.get("from") : 0)
                .bind("size", params.get("size") != null ? params.get("size") : 10)
                .fetch().all()
                .mapNotNull(LocationDto::map);
    }
}
