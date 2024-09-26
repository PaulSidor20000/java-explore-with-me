package ru.practicum.ewm.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import ru.practicum.ewm.event.dto.*;
import ru.practicum.ewm.event.entity.Event;
import ru.practicum.ewm.exceptions.BadRequestException;
import ru.practicum.ewm.exceptions.EventConditionException;
import ru.practicum.ewm.exceptions.RequestConditionException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EventValidator {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public void newEventValidator(NewEventDto dto) {
        if (dto.getEventDate() != null) {
            checkEventDate(LocalDateTime.parse(dto.getEventDate(), formatter));
        }
    }

    public Mono<Event> adminUpdateEventAdminRequestValidator(Event entity, UpdateEventAdminRequest dto) {
        if (dto.getEventDate() != null) {
            checkEventDate(LocalDateTime.parse(dto.getEventDate(), formatter));
        }
        if (dto.getEventDate() != null
                && entity.getPublishedOn() != null
                && LocalDateTime.parse(entity.getPublishedOn(), formatter).minusHours(1)
                .isAfter(
                        LocalDateTime.parse(dto.getEventDate(), formatter))) {
            throw new RequestConditionException(LocalDateTime.parse(dto.getEventDate(), formatter));
        }
        if (dto.getEventDate() != null
                && dto.getStateAction() == EventStateAction.REJECT_EVENT
                && entity.getState() == EventState.PUBLISHED) {
            throw new RequestConditionException(entity.getState());
        }
        if (entity.getState() != EventState.PENDING) {
            throw new RequestConditionException(entity.getState());
        }
        if (dto.getCategory() != null && dto.getCategory() != entity.getCategoryId()) {
            throw new RequestConditionException(String.format(
                    "Requested category id=%d not match with event category id=%d", dto.getCategory(), entity.getCategoryId()));
        }
        return Mono.just(entity);
    }

    public Mono<Event> privateUpdateEventUserRequestValidator(Event entity, AbstractionEventDto dto) {
        if (entity.getState() == EventState.PUBLISHED) {
            throw new RequestConditionException("Only pending or canceled events can be changed");
        }
        if (dto.getEventDate() != null) {
            checkEventDate(LocalDateTime.parse(dto.getEventDate(), formatter));
        }
        return Mono.just(entity);
    }

    public void validateParams(EventParams params) {
        if (params.getRangeStart() != null && params.getRangeEnd() != null) {
            LocalDateTime start = LocalDateTime.parse(params.getRangeStart(), formatter);
            LocalDateTime end = LocalDateTime.parse(params.getRangeEnd(), formatter);

            if (start.isAfter(end)) {
                throw new BadRequestException("Start time must be before end time");
            }
        }
    }

    private void checkEventDate(LocalDateTime eventDate) {
        if (LocalDateTime.now().plusHours(2).isAfter(eventDate)) {
            throw new EventConditionException(eventDate);
        }
    }
}
