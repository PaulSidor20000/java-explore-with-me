package ru.practicum.ewm.event.dto;

import io.r2dbc.spi.Row;
import lombok.Builder;
import lombok.Data;
import ru.practicum.ewm.category.dto.CategoryDto;
import ru.practicum.ewm.user.dto.UserShortDto;

@Data
@Builder
public class EventShortDto {

    private String annotation;

    private CategoryDto category;

    private Integer confirmedRequests;

    private String eventDate;

    private Integer id;

    private UserShortDto initiator;

    private Boolean paid;

    private String title;

    private Long views;

    public static EventShortDto map(Row row, Object o) {
        CategoryDto category = CategoryDto.builder()
                .id(row.get("category_id", Integer.class))
                .name(row.get("category_name", String.class))
                .build();

        UserShortDto user = UserShortDto.builder()
                .id(row.get("user_id", Integer.class))
                .name(row.get("user_name", String.class))
                .build();

        return EventShortDto.builder()
                .id(row.get("id", Integer.class))
                .annotation(row.get("annotation", String.class))
                .confirmedRequests(row.get("confirmed_requests", Integer.class))
                .eventDate(row.get("event_date", String.class))
                .paid(row.get("paid", Boolean.class))
                .title(row.get("title", String.class))
                .category(category)
                .initiator(user)
                .build();
    }

}
