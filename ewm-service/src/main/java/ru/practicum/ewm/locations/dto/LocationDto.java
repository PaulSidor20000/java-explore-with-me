package ru.practicum.ewm.locations.dto;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ru.practicum.ewm.locations.entity.AbstractLocation;

import java.util.Map;

@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class LocationDto extends AbstractLocation {

    public static LocationDto map(Map<String, Object> rows) {
        if (rows.get("lon") != null && rows.get("lat") != null) {
            return LocationDto.builder()
                    .id((Integer) rows.get("location_id"))
                    .name((String) rows.get("location_name"))
                    .lon((Float) rows.get("lon"))
                    .lat((Float) rows.get("lat"))
                    .build();
        }
        return null;
    }

}