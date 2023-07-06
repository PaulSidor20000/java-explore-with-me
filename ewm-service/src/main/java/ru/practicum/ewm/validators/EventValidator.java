package ru.practicum.ewm.validators;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import reactor.core.publisher.Mono;
import ru.practicum.ewm.event.dto.*;
import ru.practicum.ewm.event.entity.Event;
import ru.practicum.ewm.exceptions.BadRequestException;
import ru.practicum.ewm.exceptions.EventConditionException;
import ru.practicum.ewm.exceptions.RequestConditionException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EventValidator {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static void newEventValidator(NewEventDto dto) {
        if (dto.getEventDate() != null) {
            checkEventDate(LocalDateTime.parse(dto.getEventDate(), formatter));
        }
    }

    public static Mono<Event> adminUpdateEventAdminRequestValidator(Event entity, UpdateEventAdminRequest dto) {
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

        return Mono.just(entity);
    }

    public static Mono<Event> privateUpdateEventUserRequestValidator(Event entity, UpdateEventUserRequest dto) {
        if (dto.getEventDate() != null) {
            checkEventDate(LocalDateTime.parse(dto.getEventDate(), formatter));
        }

        if (dto.getEventDate() != null
                && (entity.getState() == EventState.PENDING
                || entity.getState() == EventState.CANCELED)) {
            throw new RequestConditionException("Only pending or canceled events can be changed");
        }

        return Mono.just(entity);
    }

    public static void validateAdminParams(MultiValueMap<String, String> params) {
        Optional<List<String>> rangeStart = Optional.ofNullable(params.get("rangeStart"));
        Optional<List<String>> rangeEnd = Optional.ofNullable(params.get("rangeEnd"));

//        LocalDateTime start = LocalDateTime.parse(rangeStart
//                .orElse(List.of(LocalDateTime.now().format(formatter)))
//                .get(0), formatter);
//        LocalDateTime end = LocalDateTime.parse(rangeEnd
//                .orElse(List.of(LocalDateTime.now().format(formatter)))
//                .get(0), formatter);

        LocalDateTime start = LocalDateTime.parse(rangeStart
                .orElseThrow(() -> new BadRequestException("Start range must be specify"))
                .get(0), formatter);
        LocalDateTime end = LocalDateTime.parse(rangeEnd
                .orElseThrow(() -> new BadRequestException("Start range must be specify"))
                .get(0), formatter);

        if (start.isAfter(end)) {
            throw new BadRequestException("Start time must be before end time");
        }
    }

    public static void validatePublicParams(MultiValueMap<String, String> params) {
        Optional<List<String>> rangeStart = Optional.ofNullable(params.get("rangeStart"));
        Optional<List<String>> rangeEnd = Optional.ofNullable(params.get("rangeEnd"));

        if (rangeStart.isPresent() && rangeEnd.isPresent()) {
            LocalDateTime start = LocalDateTime.parse(rangeStart
                    .get()
                    .get(0), formatter);
            LocalDateTime end = LocalDateTime.parse(rangeEnd
                    .get()
                    .get(0), formatter);

            if (start.isAfter(end)) {
                throw new BadRequestException("Start time must be before end time");
            }
        }
    }

    private static void checkEventDate(LocalDateTime eventDate) {
        if (LocalDateTime.now().plusHours(2).isAfter(eventDate)) {
            throw new EventConditionException(eventDate);
        }
    }
}
