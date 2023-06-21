package ru.practicum.ewm.exceptions;

import ru.practicum.ewm.event.dto.EventState;
import ru.practicum.ewm.request.entity.Request;

import java.time.LocalDateTime;

public class RequestConditionException extends RuntimeException {
    private final String reason = "For the requested operation the conditions are not met.";
    public RequestConditionException(EventState state) {
        super(String.format("Cannot publish the event because it's not in the right state: %s", state));
    }
    public RequestConditionException(LocalDateTime dateTime) {
        super(String.format("Cannot publish the event because it's not in the right time: %s", dateTime));
    }
    public RequestConditionException(Request request) {
        super(String.format("The same request was emitted already %s", request));
    }
    public RequestConditionException(String message) {
        super(message);
    }
}
