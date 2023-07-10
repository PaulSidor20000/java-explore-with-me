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

    public static EventFullDto map(Map<String, Object> data) {
        CategoryDto category = CategoryDto.builder()
                .id((Integer) data.get("category_id"))
                .name((String) data.get("category_name"))
                .build();

        UserShortDto user = UserShortDto.builder()
                .id((Integer) data.get("user_id"))
                .name((String) data.get("user_name"))
                .build();

        Location location = Location.builder()
                .lat((Float) data.get("lat"))
                .lon((Float) data.get("lon"))
                .build();

        return EventFullDto.builder()
                .id((Integer) data.get("id"))
                .annotation((String) data.get("annotation"))
                .confirmedRequests((Long) data.get("confirmed_requests"))
                .createdOn((String) data.get("created_on"))
                .description((String) data.get("description"))
                .eventDate((String) data.get("event_date"))
                .paid((Boolean) data.get("paid"))
                .participantLimit((Integer) data.get("participant_limit"))
                .publishedOn((String) data.get("published_on"))
                .requestModeration((Boolean) data.get("request_moderation"))
                .state((String) data.get("event_state"))
                .title((String) data.get("title"))
                .category(category)
                .initiator(user)
                .location(location)
                .build();
    }

}