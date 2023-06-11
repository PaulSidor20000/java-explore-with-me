package ru.practicum.ewm.dto;

import lombok.Data;

@Data
public class EventShortDto {

    private String annotation;

    private CategoryDto category;

    private int confirmedRequests;

    private String eventDate;

    private int id;

    private UserShortDto initiator;

    private boolean paid;

    private String title;

    private int views;

}
