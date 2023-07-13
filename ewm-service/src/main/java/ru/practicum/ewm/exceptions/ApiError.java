package ru.practicum.ewm.exceptions;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Data
@Builder
public class ApiError {
    private List<String> errors;
    private String message;
    private String reason;
    private HttpStatus status;
    private final String timestamp = LocalDateTime
            .now()
            .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
}
