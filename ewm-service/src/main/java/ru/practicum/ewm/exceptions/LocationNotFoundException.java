package ru.practicum.ewm.exceptions;

public class LocationNotFoundException extends RuntimeException {
    private static final String REASON = "The required object was not found.";

    public LocationNotFoundException(int id) {
        super(String.format("Location with id=%d was not found", id));
    }

    @Override
    public String getLocalizedMessage() {
        return REASON;
    }

}
