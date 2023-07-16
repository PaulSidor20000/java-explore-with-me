package ru.practicum.ewm.event.dto;

import lombok.Builder;
import lombok.Data;
import ru.practicum.ewm.category.dto.CategoryDto;
import ru.practicum.ewm.user.dto.UserShortDto;

import java.util.Map;

@Data
@Builder
public class EventShortDto {

    private String annotation;

    private CategoryDto category;

    private Long confirmedRequests;

    private String eventDate;

    private Integer id;

    private UserShortDto initiator;

    private Boolean paid;

    private String title;

    private Long views;

    public static EventShortDto map(Map<String, Object> rows) {
        if (rows.get("event_id") != null) {
            return EventShortDto.builder()
                    .id((Integer) rows.get("event_id"))
                    .annotation((String) rows.get("annotation"))
                    .confirmedRequests((Long) rows.get("confirmed_requests"))
                    .eventDate((String) rows.get("event_date"))
                    .paid((Boolean) rows.get("paid"))
                    .title((String) rows.get("event_title"))
                    .category(CategoryDto.map(rows))
                    .initiator(UserShortDto.map(rows))
                    .build();
        }
        return null;
    }

}
