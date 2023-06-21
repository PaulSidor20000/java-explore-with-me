package ru.practicum.ewm.compilation.dto;

import lombok.Data;
import ru.practicum.ewm.event.dto.EventShortDto;

import java.util.Set;

@Data
public class CompilationDto {

    private Set<EventShortDto> events;

    private int id;

    private boolean pinned;

    private String title;
}
