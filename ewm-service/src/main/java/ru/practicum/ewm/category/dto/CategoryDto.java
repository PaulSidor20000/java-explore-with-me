package ru.practicum.ewm.category.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class CategoryDto {

    private Integer id;

    private String name;

    public static CategoryDto map(Map<String, Object> rows) {
        if (rows.get("category_id") != null) {
            return CategoryDto.builder()
                    .id((Integer) rows.get("category_id"))
                    .name((String) rows.get("category_name"))
                    .build();
        }
        return null;
    }

}
