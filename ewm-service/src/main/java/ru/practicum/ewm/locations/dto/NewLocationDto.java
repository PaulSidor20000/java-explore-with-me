package ru.practicum.ewm.locations.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.practicum.ewm.locations.entity.AbstractLocation;

@Data
@EqualsAndHashCode(callSuper = true)
public class NewLocationDto extends AbstractLocation {
}