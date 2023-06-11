package ru.practicum.ewm.event;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@ToString
@Table("events")
public class Event {

    @Id
    private int id;

    private String annotation;

    private String description;

    private String eventDate;

    private boolean paid;

    private int participantLimit;

    private boolean requestModeration;

    private String title;

    private String state;

    private float lat;

    private float lon;
}
