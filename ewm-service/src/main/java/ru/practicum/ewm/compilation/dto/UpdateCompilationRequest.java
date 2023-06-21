package ru.practicum.ewm.compilation.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

@Data
public class UpdateCompilationRequest {

    private int events;

    private boolean pinned;

    @Range(min = 1, max = 50)
    private String title;
}
