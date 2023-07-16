package ru.practicum.ewm.exceptions;

public class CategoryNotFoundException extends RuntimeException {
    private static final String REASON = "The required object was not found.";

    public CategoryNotFoundException(int id) {
        super(String.format("Category with id=%d was not found", id));
    }

    @Override
    public String getLocalizedMessage() {
        return REASON;
    }

}
