package ru.practicum.ewm.exceptions;

public class CategoryNotFoundException extends RuntimeException {
    public CategoryNotFoundException(int id) {
        super(String.format("Category with id=%d was not found", id));
    }
}
