package ru.practicum.ewm.compilation.dto;

import lombok.Data;

import java.util.Set;

@Data
public class NewCompilationDto {

    private Set<Integer> events;

    private boolean pinned;

    private String title;

}
