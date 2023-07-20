package ru.practicum.ewm.event.dto;

import lombok.Data;
import ru.practicum.ewm.locations.dto.LocationDto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class NewEventDto {

    @NotBlank
    @Size(min = 20, max = 2000)
    private String annotation;

    private Integer category;

    @NotBlank
    @Size(min = 20, max = 7000)
    private String description;

    private String eventDate;

    private LocationDto location;

    private Boolean paid;

    private Integer participantLimit;

    private Boolean requestModeration = true;

    @NotBlank
    @Size(min = 3, max = 120)
    private String title;

}
