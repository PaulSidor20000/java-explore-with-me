package ru.practicum.ewm.locations.repository;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.test.context.jdbc.Sql;
import reactor.test.StepVerifier;
import ru.practicum.ewm.locations.dto.LocationDto;
import ru.practicum.ewm.locations.dto.LocationParams;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataR2dbcTest
@Sql(value = "/testdata.sql")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class CustomLocationRepositoryImplTest {
    private final CustomLocationRepository customLocationRepository;
    private final LocationDto locationDto = LocationDto.builder().id(1).lon(-45.5376F).lat(-5.0462F).name("Бразилия, Мараньян").build();
    private final LocationParams params = LocationParams.builder().lon(-45.5376F).lat(-5.0462F).radius(1).build();

    @Test
    void findLocationsByParams() {
        customLocationRepository.findLocationsByParams(params)
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