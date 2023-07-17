package ru.practicum.ewm.locations.dto;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import ru.practicum.ewm.locations.entity.Location;

@Mapper(componentModel = "spring")
public interface LocationMapper {

    LocationDto map(Location location);

    Location map(NewLocationDto dto);

    Location merge(@MappingTarget Location location, NewLocationDto dto);

}
