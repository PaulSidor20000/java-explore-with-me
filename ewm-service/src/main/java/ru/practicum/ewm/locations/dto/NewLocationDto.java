package ru.practicum.ewm.locations.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
public class NewLocationDto {

    @NotBlank
    @Size(min = 2, max = 255)
    private String name;

    @NotNull
    private Float lat;

    @NotNull
    private Float lon;

}