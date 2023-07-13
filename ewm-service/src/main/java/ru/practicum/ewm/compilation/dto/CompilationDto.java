package ru.practicum.ewm.compilation.dto;

import lombok.Builder;
import lombok.Data;
import ru.practicum.ewm.event.dto.EventShortDto;

import java.util.*;
import java.util.stream.Collectors;

@Data
@Builder
public class CompilationDto {

    @Builder.Default
    private Set<EventShortDto> events = new HashSet<>();

    private Integer id;

    private Boolean pinned;

    private String title;

    public static CompilationDto map(List<Map<String, Object>> rows) {
        return CompilationDto.builder()
                .id((Integer) rows.get(0).get("compilation_id"))
                .pinned((Boolean) rows.get(0).get("compilation_pinned"))
                .title((String) rows.get(0).get("compilation_title"))
                .events(rows.stream()
                        .map(EventShortDto::map)
                        .filter(Objects::nonNull)
                        .collect(Collectors.toSet()))
                .build();
    }

}
