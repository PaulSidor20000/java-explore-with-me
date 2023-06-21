package ru.practicum.ewm.request.dto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.ewm.request.entity.Request;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Mapper(
        componentModel = "spring",
        imports = {
                RequestStatus.class,
                LocalDateTime.class,
                DateTimeFormatter.class
        })
public interface RequestMapper {

    @Mapping(target = "requester", source = "userId")
    @Mapping(target = "event", source = "eventId")
    @Mapping(target = "status", expression = "java(isRequestModeration ? RequestStatus.PENDING : RequestStatus.CONFIRMED)")
    @Mapping(target = "created", expression = "java(LocalDateTime.now().format(DateTimeFormatter.ofPattern(\"yyyy-MM-dd HH:mm:ss\")))")
    Request merge(Integer userId, Integer eventId, Boolean isRequestModeration);

    ParticipationRequestDto map(Request entity);

}
