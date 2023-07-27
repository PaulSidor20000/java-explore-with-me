package ru.practicum.ewm.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class DtoValidator {
    private final Validator validator;

//    public <T> void validate(T dto) {
//        Set<ConstraintViolation<T>> violations = validator.validate(dto);
//        if (!violations.isEmpty()) {
//            throw new ConstraintViolationException(violations);
//        }
//    }

}
