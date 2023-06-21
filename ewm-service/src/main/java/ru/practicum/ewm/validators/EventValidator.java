package ru.practicum.ewm.validators;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import ru.practicum.ewm.event.dto.EventState;
import ru.practicum.ewm.event.dto.EventStateAction;
import ru.practicum.ewm.event.dto.UpdateEventAdminRequest;
import ru.practicum.ewm.event.dto.UpdateEventUserRequest;
import ru.practicum.ewm.event.entity.Event;
import ru.practicum.ewm.exceptions.RequestConditionException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EventValidator {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static Mono<Event> adminEventValidator(Event entity, UpdateEventAdminRequest dto) {

        if (dto.getEventDate() != null
                && !LocalDateTime.parse(dto.getEventDate(), formatter)
                .isBefore(
                        LocalDateTime.parse(entity.getCreatedOn(), formatter).minusHours(1))) {
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

        return Mono.just(entity);
    }

    public static Mono<Event> privateEventValidator(Event entity, UpdateEventUserRequest dto) {
        if (dto.getEventDate() != null
                && !LocalDateTime.parse(dto.getEventDate(), formatter)
                .isBefore(
                        LocalDateTime.now().minusHours(2))) {
            throw new RequestConditionException(LocalDateTime.parse(dto.getEventDate(), formatter));
        }

        if (dto.getEventDate() != null
                && (entity.getState() == EventState.PENDING
                || entity.getState() == EventState.CANCELED)) {
            throw new RequestConditionException("Only pending or canceled events can be changed");
        }

        return Mono.just(entity);
    }

}
