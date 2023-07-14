package ru.practicum.ewm.exceptions;

import java.time.LocalDateTime;

public class EventConditionException extends RuntimeException {
    private static final String REASON = "For the requested operation the conditions are not met.";

    public EventConditionException(LocalDateTime dateTime) {
        super(String.format("Field: eventDate. Error: должно содержать дату, которая еще не наступила. Value: %s", dateTime));
    }

    public EventConditionException(String message) {
        super(message);
    }

    @Override
    public String getLocalizedMessage() {
        return REASON;
    }
}
