package ru.practicum.ewm.event.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import ru.practicum.ewm.event.dto.EventState;

import java.util.Objects;

@Getter
@Setter
@ToString
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return id == event.id
                && userId == event.userId
                && categoryId == event.categoryId
                && paid == event.paid
                && participantLimit == event.participantLimit
                && requestModeration == event.requestModeration
                && Float.compare(event.lat, lat) == 0
                && Float.compare(event.lon, lon) == 0
                && Objects.equals(annotation, event.annotation)
                && Objects.equals(description, event.description)
                && Objects.equals(eventDate, event.eventDate)
                && Objects.equals(title, event.title)
                && state == event.state
                && Objects.equals(createdOn, event.createdOn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, categoryId, annotation, description, eventDate, paid, participantLimit, requestModeration, title, state, lat, lon, createdOn);
    }
}
