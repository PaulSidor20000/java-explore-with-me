package ru.practicum.ewm.event.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class Location {

    private Float lat;

    private Float lon;

    public static Location map(Map<String, Object> rows) {
        if (rows.get("lat") != null) {
            return Location.builder()
                    .lat((Float) rows.get("lat"))
                    .lon((Float) rows.get("lon"))
                    .build();
        }
        return null;
    }

}