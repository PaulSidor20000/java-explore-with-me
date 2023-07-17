package ru.practicum.ewm.locations.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;

import java.util.Map;

@Data
@Builder
public class LocationDto {

    private String name;

    private Float lat;

    private Float lon;

    public static LocationDto map(Map<String, Object> rows) {
        if (rows.get("lat") != null) {
            return LocationDto.builder()
                    .name((String) rows.get("location_name"))
                    .lat((Float) rows.get("lat"))
                    .lon((Float) rows.get("lon"))
                    .build();
        }
        return null;
    }

}