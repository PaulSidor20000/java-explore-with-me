package ru.practicum.ewm.user.dto;

import io.r2dbc.spi.Row;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {

    private Integer id;

    private String name;

    private String email;

    public static UserDto map(Row row, Object o) {
        return UserDto.builder()
                .id(row.get("id", Integer.class))
                .name(row.get("name", String.class))
                .email(row.get("email", String.class))
                .build();
    }

}
