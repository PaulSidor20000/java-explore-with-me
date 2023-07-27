package ru.practicum.ewm.compilation.dto;

import lombok.Data;

import javax.validation.constraints.Size;
import java.util.Set;

@Data
public class UpdateCompilationRequest {

    private Set<Integer> events;

    private Boolean pinned;

    @Size(min = 1, max = 50)
    private String title;

}
