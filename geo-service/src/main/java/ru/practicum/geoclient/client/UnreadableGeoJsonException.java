package ru.practicum.geoclient.client;

public class UnreadableGeoJsonException extends RuntimeException {
    public UnreadableGeoJsonException(String message) {
        super(message);
    }
}
