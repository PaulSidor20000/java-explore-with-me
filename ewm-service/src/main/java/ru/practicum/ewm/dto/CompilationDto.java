package ru.practicum.ewm.dto;

import lombok.Data;

import java.util.Set;

@Data
public class CompilationDto {

    private Set<EventShortDto> events;

    private int id;

    private boolean pinned;

    private String title;
}
