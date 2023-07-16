package ru.practicum.ewm.event.dto;

import lombok.Builder;
import lombok.Data;
import ru.practicum.ewm.category.dto.CategoryDto;
import ru.practicum.ewm.user.dto.UserShortDto;

import java.util.Map;

@Data
@Builder
public class EventFullDto {

    private String annotation;

    private CategoryDto category;

    private Long confirmedRequests;

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

    private Long views;

    public static EventFullDto map(Map<String, Object> rows) {
        if (rows.get("event_id") != null) {
            return EventFullDto.builder()
                    .id((Integer) rows.get("event_id"))
                    .annotation((String) rows.get("annotation"))
                    .confirmedRequests((Long) rows.get("confirmed_requests"))
                    .createdOn((String) rows.get("created_on"))
                    .description((String) rows.get("description"))
                    .eventDate((String) rows.get("event_date"))
                    .paid((Boolean) rows.get("paid"))
                    .participantLimit((Integer) rows.get("participant_limit"))
                    .publishedOn((String) rows.get("published_on"))
                    .requestModeration((Boolean) rows.get("request_moderation"))
                    .state((String) rows.get("event_state"))
                    .title((String) rows.get("event_title"))
                    .category(CategoryDto.map(rows))
                    .initiator(UserShortDto.map(rows))
                    .location(Location.map(rows))
                    .build();
        }
        return null;
    }

}