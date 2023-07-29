package ru.practicum.ewm.locations.repository;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.test.context.jdbc.Sql;
import reactor.test.StepVerifier;
import ru.practicum.ewm.locations.dto.LocationParams;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataR2dbcTest
@Sql(value = "/testdata.sql")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class CustomLocationRepositoryImplTest {
    private final LocationRepository locationRepository;
    private final LocationParams params = LocationParams.builder().lon(-45.5376F).lat(-5.0462F).radius(1).build();

    @Test
    @Disabled("Syntax error in SQL statement " +
            "\"CREATE OR REPLACE FUNCTION " +
            "distance(lat1 float, lon1 float, lat2 float, lon2 float) RETURNS float AS [*]'\\000a    " +
            "declare\\000a        dist      float = 0\";")
    void findLocationsByParams() {
        locationRepository.findLocationsByParams(params)
                .as(StepVerifier::create)
                .expectNextCount(1)
                .consumeNextWith(dto -> {
                    assertEquals(1, dto.getId());
                    assertEquals(-45.5376F, dto.getLon());
                    assertEquals(-5.0462F, dto.getLat());
                    assertEquals("Бразилия, Мараньян", dto.getName());
                })
                .verifyComplete();
    }
}