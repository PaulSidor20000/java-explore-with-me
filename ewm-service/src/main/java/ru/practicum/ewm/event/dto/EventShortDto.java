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

    public static EventShortDto map(Map<String, Object> data) {
        CategoryDto category = CategoryDto.builder()
                .id((Integer) data.get("category_id"))
                .name((String) data.get("category_name"))
                .build();

        UserShortDto user = UserShortDto.builder()
                .id((Integer) data.get("user_id"))
                .name((String) data.get("user_name"))
                .build();

        return EventShortDto.builder()
                .id((Integer) data.get("id"))
                .annotation((String) data.get("annotation"))
                .confirmedRequests((Long) data.get("confirmed_requests"))
                .eventDate((String) data.get("event_date"))
                .paid((Boolean) data.get("paid"))
                .title((String) data.get("title"))
                .category(category)
                .initiator(user)
                .build();
    }

}
