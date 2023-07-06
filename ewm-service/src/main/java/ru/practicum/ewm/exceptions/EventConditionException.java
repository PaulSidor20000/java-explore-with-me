package ru.practicum.ewm.exceptions;

import ru.practicum.ewm.event.dto.EventState;
import ru.practicum.ewm.request.entity.Request;

import java.time.LocalDateTime;

public class EventConditionException extends RuntimeException {
    private final String reason = "For the requested operation the conditions are not met.";

    public EventConditionException(LocalDateTime dateTime) {
        super(String.format("Field: eventDate. Error: должно содержать дату, которая еще не наступила. Value: %s", dateTime));
    }

    //    public EventConditionException(EventState state) {
    //        super(String.format("Cannot publish the event because it's not in the right state: %s", state));
    //    }
//    public EventConditionException(Request request) {
//        super(String.format("The same request was emitted already %s", request));
//    }
    public EventConditionException(String message) {
        super(message);
    }

    public String getReason() {
        return reason;
    }
}
