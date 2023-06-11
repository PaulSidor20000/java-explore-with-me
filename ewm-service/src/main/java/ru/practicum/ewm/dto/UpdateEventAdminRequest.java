package ru.practicum.ewm.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

@Data
public class UpdateEventAdminRequest {

    @Range(min = 20, max = 2000)
    private String annotation;

    private int category;

    @Range(min = 20, max = 7000)
    private String description;

    private String eventDate;

    private Location location;

    private boolean paid;

    private int participantLimit;

    private boolean requestModeration;

    private EventStateAction stateAction;

    @Range(min = 3, max = 120)
    private String title;

}
