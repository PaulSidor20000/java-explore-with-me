package ru.practicum.ewm.exceptions;

public class CategoryConditionException extends RuntimeException {
    private static final String REASON = "For the requested operation the conditions are not met.";

    public <T> CategoryConditionException(T entity) {
        super(String.format("The category is not empty %s", entity));
    }

    public CategoryConditionException(String message) {
        super(message);
    }

    @Override
    public String getLocalizedMessage() {
        return REASON;
    }

}
