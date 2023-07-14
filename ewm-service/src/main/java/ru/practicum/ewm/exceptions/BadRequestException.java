package ru.practicum.ewm.exceptions;

public class BadRequestException extends RuntimeException {
    private static final String REASON = "Incorrectly made request.";

    public BadRequestException(String message) {
        super(message);
    }

    @Override
    public String getLocalizedMessage() {
        return REASON;
    }

}
