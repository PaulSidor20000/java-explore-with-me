package ru.practicum.ewm.event.dto;

import org.mapstruct.*;
import ru.practicum.ewm.event.entity.Event;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        imports = {
                EventState.class,
                EventStateAction.class,
                LocalDateTime.class,
                DateTimeFormatter.class
        })
public interface EventMapper {

    @Mapping(target = "userId", source = "userId")
    @Mapping(target = "categoryId", source = "dto.category")
    @Mapping(target = "state", expression = "java(EventState.PENDING)")
    @Mapping(target = "lat", expression = "java(dto.getLocation().getLat())")
    @Mapping(target = "lon", expression = "java(dto.getLocation().getLon())")
    @Mapping(target = "createdOn", expression = "java(LocalDateTime.now().format(DateTimeFormatter.ofPattern(\"yyyy-MM-dd HH:mm:ss\")))")
    Event merge(int userId, NewEventDto dto);


    @Mapping(target = "state", expression = "java(" +
            " dto.getStateAction() != null" +
            " ? dto.getStateAction() == EventStateAction.PUBLISH_EVENT" +
            " ? EventState.PUBLISHED" +
            " : EventState.CANCELED" +
            " : entity.getState()" +
            ")")
    @Mapping(target = "lat", expression = "java(dto.getLocation() != null ? dto.getLocation().getLat() : entity.getLat())")
    @Mapping(target = "lon", expression = "java(dto.getLocation() != null ? dto.getLocation().getLon() : entity.getLon())")
    Event merge(@MappingTarget Event entity, UpdateEventUserRequest dto);

    @Mapping(target = "state", expression = "java(" +
            " dto.getStateAction() != null" +
            " ? dto.getStateAction() == EventStateAction.PUBLISH_EVENT" +
            " ? EventState.PUBLISHED" +
            " : EventState.CANCELED" +
            " : entity.getState()" +
            ")")
    @Mapping(target = "lat", expression = "java(dto.getLocation() != null ? dto.getLocation().getLat() : entity.getLat())")
    @Mapping(target = "lon", expression = "java(dto.getLocation() != null ? dto.getLocation().getLon() : entity.getLon())")
    Event merge(@MappingTarget Event entity, UpdateEventAdminRequest dto);

}
