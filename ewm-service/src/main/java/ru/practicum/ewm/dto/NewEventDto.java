package ru.practicum.ewm.dto;

import lombok.Data;

import javax.validation.constraints.Size;

@Data
public class NewEventDto {

    @Size(min = 20, max = 2000)
    private String annotation;

    private int category;

    @Size(min = 20, max = 7000)
    private String description;

    private String eventDate;

    private Location location;

    private boolean paid;

    private int participantLimit;

    private boolean requestModeration;

    @Size(min = 3, max = 120)
    private String title;

}
