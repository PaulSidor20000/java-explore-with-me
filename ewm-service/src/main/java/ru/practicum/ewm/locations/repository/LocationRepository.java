package ru.practicum.ewm.locations.repository;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.ewm.locations.entity.Location;


@Repository
public interface LocationRepository extends R2dbcRepository<Location, Integer>, CustomLocationRepository {
}
