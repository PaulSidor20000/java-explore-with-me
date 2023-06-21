package ru.practicum.ewm.event.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateEventAdminRequest {

    private String annotation;

    private Integer category;

    private String description;

    private String eventDate;

    private Location location;

    private Boolean paid;

    private Integer participantLimit;

    private Boolean requestModeration;

    private String title;

    private EventStateAction stateAction;

}
