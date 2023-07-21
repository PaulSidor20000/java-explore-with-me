package ru.practicum.ewm.exceptions;

public class LocationConditionException extends RuntimeException {
    private static final String REASON = "For the requested operation the conditions are not met.";

    public <T> LocationConditionException(T entity) {
        super(String.format("The location depends on event %s", entity));
    }

    public LocationConditionException(String message) {
        super(message);
    }

    @Override
    public String getLocalizedMessage() {
        return REASON;
    }

}
