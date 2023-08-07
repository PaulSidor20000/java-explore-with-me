package ru.practicum.ewm.exceptions;

public class NotFoundException extends RuntimeException {
    private static final String REASON = "The required object was not found.";

    public NotFoundException(String message) {
        super(message);
    }

    @Override
    public String getLocalizedMessage() {
        return REASON;
    }

}
