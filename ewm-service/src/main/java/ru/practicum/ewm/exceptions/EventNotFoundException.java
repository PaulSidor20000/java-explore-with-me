package ru.practicum.ewm.exceptions;

public class EventNotFoundException extends RuntimeException {
    private static final String REASON = "The required object was not found.";

    public EventNotFoundException(int id) {
        super(String.format("Event with id=%d was not found", id));
    }

    @Override
    public String getLocalizedMessage() {
        return REASON;
    }

}
