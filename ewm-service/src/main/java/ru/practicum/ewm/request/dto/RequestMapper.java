package ru.practicum.ewm.request.dto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import ru.practicum.ewm.event.entity.Event;
import ru.practicum.ewm.request.entity.Request;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Mapper(
        componentModel = "spring",
        imports = {LocalDateTime.class, DateTimeFormatter.class}
)
public interface RequestMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "requester", source = "requesterId")
    @Mapping(target = "event", source = "event.id")
    @Mapping(target = "status", source = "event", qualifiedByName = "getRequestStatus")
    @Mapping(target = "created", expression = "java(LocalDateTime.now().format(DateTimeFormatter.ofPattern(\"yyyy-MM-dd HH:mm:ss\")))")
    Request merge(Integer requesterId, Event event);

    ParticipationRequestDto map(Request entity);

    @Named(value = "getRequestStatus")
    default RequestStatus getRequestStatus(Event event) {
        if (event != null) {
            return !event.isRequestModeration() || event.getParticipantLimit() == 0
                    ? RequestStatus.CONFIRMED
                    : RequestStatus.PENDING;
        }
        return null;
    }

}
