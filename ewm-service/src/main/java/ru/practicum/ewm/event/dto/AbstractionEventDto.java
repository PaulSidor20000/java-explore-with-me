package ru.practicum.ewm.event.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ru.practicum.ewm.locations.dto.LocationDto;
import ru.practicum.ewm.locations.entity.AbstractLocation;

@Data
@SuperBuilder
@NoArgsConstructor
public abstract class AbstractionEventDto {
    private Integer category;
    private String eventDate;
    private AbstractLocation location;
//    private LocationDto location;
    private Boolean paid;
    private Integer participantLimit;
}
