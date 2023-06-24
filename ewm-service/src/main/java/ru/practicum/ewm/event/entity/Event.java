package ru.practicum.ewm.event.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import ru.practicum.ewm.event.dto.EventState;

@Data
@Table(name = "events")
public class Event {

    @Id
    private int id;

    @Column(value = "user_id")
    private int userId;

    @Column(value = "category_id")
    private int categoryId;

    private String annotation;

    private String description;

    @Column(value = "event_date")
    private String eventDate;

    private boolean paid;

    @Column(value = "participant_limit")
    private int participantLimit;

    @Column(value = "request_moderation")
    private boolean requestModeration;

    private String title;

    @Column(value = "event_state")
    private EventState state;

    private float lat;

    private float lon;

    @Column(value = "created_on")
    private String createdOn;

    @Column(value = "published_on")
    private String publishedOn;

}
