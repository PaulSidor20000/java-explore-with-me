package ru.practicum.ewm.event.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.Size;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
public class UpdateEventAdminRequest extends AbstractionEventDto {

    @Size(min = 20, max = 2000)
    private String annotation;

    @Size(min = 20, max = 7000)
    private String description;

    @Size(min = 3, max = 120)
    private String title;

    private Boolean requestModeration;

    private EventStateAction stateAction;

}
