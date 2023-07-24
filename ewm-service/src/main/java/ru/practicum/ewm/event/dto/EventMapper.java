package ru.practicum.ewm.event.dto;

import org.mapstruct.*;
import ru.practicum.ewm.event.entity.Event;
import ru.practicum.statdto.dto.ViewStats;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        imports = {LocalDateTime.class, DateTimeFormatter.class}
)
public interface EventMapper {

    @Mapping(target = "userId", source = "userId")
    @Mapping(target = "categoryId", source = "dto.category")
    @Mapping(target = "locationId", source = "dto.location.id")
    @Mapping(target = "state", expression = "java(EventState.PENDING)")
    @Mapping(target = "createdOn", expression = "java(LocalDateTime.now().format(DateTimeFormatter.ofPattern(\"yyyy-MM-dd HH:mm:ss\")))")
    Event merge(int userId, NewEventDto dto);

    @Mapping(target = "state", source = "stateAction", qualifiedByName = "getEventStateOfUserRequest")
    Event merge(@MappingTarget Event entity, UpdateEventUserRequest dto);

    @Mapping(target = "state", source = "stateAction", qualifiedByName = "getEventStateOfAdminRequest")
    @Mapping(target = "publishedOn", source = "stateAction", qualifiedByName = "getPublishedOnDate")
    Event merge(@MappingTarget Event entity, UpdateEventAdminRequest dto);

    @Mapping(target = "views", source = "hits")
    EventFullDto enrich(@MappingTarget EventFullDto dto, ViewStats viewStats);

    @Mapping(target = "views", source = "hits")
    EventShortDto enrich(@MappingTarget EventShortDto dto, ViewStats viewStats);

    @Named(value = "getEventStateOfAdminRequest")
    default EventState getEventStateOfAdminRequest(EventStateAction stateAction) {
        if (stateAction != null) {
            return stateAction == EventStateAction.PUBLISH_EVENT
                    ? EventState.PUBLISHED
                    : EventState.CANCELED;
        }
        return null;
    }

    @Named(value = "getEventStateOfUserRequest")
    default EventState getEventStateOfUserRequest(EventStateAction stateAction) {
        if (stateAction != null) {
            return stateAction == EventStateAction.SEND_TO_REVIEW
                    ? EventState.PENDING
                    : EventState.CANCELED;
        }
        return null;
    }

    @Named(value = "getPublishedOnDate")
    default String getPublishedOnDate(EventStateAction stateAction) {
        if (stateAction == EventStateAction.PUBLISH_EVENT) {
            return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        }
        return null;
    }

}
