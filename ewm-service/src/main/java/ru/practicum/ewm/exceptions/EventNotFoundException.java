package ru.practicum.ewm.exceptions;

public class EventNotFoundException extends RuntimeException {
    public EventNotFoundException(int id) {
        super(String.format("Event with id=%d was not found", id));
    }
}
