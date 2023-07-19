package ru.practicum.ewm.locations.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.practicum.ewm.locations.entity.AbstractLocation;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(callSuper = true)
public class NewLocationDto extends AbstractLocation {

    @NotBlank
    private String name;

    @NotNull
    private Float lat;

    @NotNull
    private Float lon;

}