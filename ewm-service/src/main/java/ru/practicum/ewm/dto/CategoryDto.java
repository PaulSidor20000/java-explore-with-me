package ru.practicum.ewm.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class CategoryDto {

    private int id;

    @NotBlank
    @Size(min = 1, max = 50)
    private String name;
}
