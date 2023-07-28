package ru.practicum.ewm.locations.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.PositiveOrZero;

@Data
@Builder
public class LocationParams {
    private Float lon;
    private Float lat;

    @PositiveOrZero
    private Integer radius;
    private Integer from;
    private Integer size;

}
