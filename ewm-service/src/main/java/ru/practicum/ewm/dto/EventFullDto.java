package ru.practicum.ewm.dto;

import lombok.Data;

@Data
public class EventFullDto {

    private String annotation;

    private CategoryDto category;

    private int confirmedRequests;

    private String createdOn;

    private String description;

    private String eventDate;

    private int id;

    private UserShortDto initiator;

    private Location location;

    private boolean paid;

    private int participantLimit;

    private String publishedOn;

    private boolean requestModeration;

    private EventState state;

    private String title;

    private int views;

}