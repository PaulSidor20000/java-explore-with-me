package ru.practicum.ewm.event;

import org.mapstruct.Mapper;
import ru.practicum.ewm.dto.EventFullDto;
import ru.practicum.ewm.dto.NewEventDto;

@Mapper(componentModel = "spring")
public interface EventMapper {

    Event map(NewEventDto dto);

    EventFullDto map(Event event);
}
