package ru.practicum.ewm.user.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class UserDto {

    private Integer id;

    private String name;

    private String email;

    public static UserDto map(Map<String, Object> rows) {
        if (rows.get("user_id") != null) {
            return UserDto.builder()
                    .id((Integer) rows.get("user_id"))
                    .name((String) rows.get("user_name"))
                    .email((String) rows.get("user_email"))
                    .build();
        }
        return null;
    }

}
