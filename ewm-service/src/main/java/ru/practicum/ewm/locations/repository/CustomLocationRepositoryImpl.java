package ru.practicum.ewm.locations.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.r2dbc.core.DatabaseClient;
import reactor.core.publisher.Flux;
import ru.practicum.ewm.locations.dto.LocationDto;


@RequiredArgsConstructor
public class CustomLocationRepositoryImpl implements CustomLocationRepository {
    private final DatabaseClient client;

    @Override
    public Flux<LocationDto> findLocationsByParams(Integer radius, Float lon, Float lat, Integer from, Integer size) {
        String query = "SELECT l.*" +
                " FROM locations AS l" +
                " WHERE distance(l.lat, l.lon, :lat2::real, :lon2::real) < :radius::real" +
                " LIMIT (:size)::bigint OFFSET (:from)::bigint;";

        return client.sql(query)
                .bind("radius", radius)
                .bind("lon2", lon)
                .bind("lat2", lat)
                .bind("from", from)
                .bind("size", size)
                .fetch().all()
                .mapNotNull(LocationDto::map);
    }
}
