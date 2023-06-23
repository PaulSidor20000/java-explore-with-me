package ru.practicum.ewm.exceptions;

public class UserNotFoundException extends RuntimeException {

    String reason = "The required object was not found.";
    public UserNotFoundException(int id) {
        super(String.format("User with id=%d was not found", id));
    }

    public String getReason() {
        return reason;
    }
}
