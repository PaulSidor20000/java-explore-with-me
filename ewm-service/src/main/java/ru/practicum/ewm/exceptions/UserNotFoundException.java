package ru.practicum.ewm.exceptions;

public class UserNotFoundException extends RuntimeException {

    private static final String REASON = "The required object was not found.";

    public UserNotFoundException(int id) {
        super(String.format("User with id=%d was not found", id));
    }

    @Override
    public String getLocalizedMessage() {
        return REASON;
    }
}
