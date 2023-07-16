package ru.practicum.ewm.user.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class UserShortDto {

    private Integer id;

    private String name;

    public static UserShortDto map(Map<String, Object> rows) {
        if (rows.get("user_id") != null) {
            return UserShortDto.builder()
                    .id((Integer) rows.get("user_id"))
                    .name((String) rows.get("user_name"))
                    .build();
        }
        return null;
    }

}
