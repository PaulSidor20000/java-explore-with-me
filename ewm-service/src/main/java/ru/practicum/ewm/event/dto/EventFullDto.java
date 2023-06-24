package ru.practicum.ewm.event.dto;

import io.r2dbc.spi.Row;
import lombok.Builder;
import lombok.Data;
import ru.practicum.ewm.category.dto.CategoryDto;
import ru.practicum.ewm.user.dto.UserShortDto;

@Data
@Builder
public class EventFullDto {

    private String annotation;

    private CategoryDto category;

    private Integer confirmedRequests;

    private String createdOn;

    private String description;

    private String eventDate;

    private Integer id;

    private UserShortDto initiator;

    private Location location;

    private Boolean paid;

    private Integer participantLimit;

    private String publishedOn;

    private Boolean requestModeration;

    private String state;

    private String title;

    private Integer views;

    public static EventFullDto map(Row row, Object o) {
        CategoryDto category = CategoryDto.builder()
                .id(row.get("category_id", Integer.class))
                .name(row.get("category_name", String.class))
                .build();

        UserShortDto user = UserShortDto.builder()
                .id(row.get("user_id", Integer.class))
                .name(row.get("user_name", String.class))
                .build();

        Location location = Location.builder()
                .lat(row.get("lat", Float.class))
                .lon(row.get("lon", Float.class))
                .build();

        return EventFullDto.builder()
                .id(row.get("id", Integer.class))
                .annotation(row.get("annotation", String.class))
                //  .confirmedRequests(row.get("annotation", Integer.class))
                .createdOn(row.get("created_on", String.class))
                .description(row.get("description", String.class))
                .eventDate(row.get("event_date", String.class))
                .paid(row.get("paid", Boolean.class))
                .participantLimit(row.get("participant_limit", Integer.class))
                .publishedOn(row.get("published_on", String.class))
                .requestModeration(row.get("request_moderation", Boolean.class))
                .state(row.get("event_state", String.class))
                .title(row.get("title", String.class))
                //  .views(row.get("views", Integer.class))
                .category(category)
                .initiator(user)
                .location(location)
                .build();
    }

}