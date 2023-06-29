package ru.practicum.ewm.exceptions;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import javax.validation.ConstraintViolationException;
import java.util.List;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ErrorHandler {
    public static final String A_ERROR = "error";
    public static final String NOT_FOUND = "The required object was not found.";
    public static final String LOG_ERROR = "Error message: {}";
    public static final String SERVER_ERROR = "Server error:";

    public static Mono<ServerResponse> handler(Throwable error) {
        if (isStatus400(error)) {
            return response(error, HttpStatus.BAD_REQUEST);
        }
        if (isStatus404(error)) {
            return response(error, HttpStatus.NOT_FOUND);
        }
        if (isStatus409(error)) {
            return response(error, HttpStatus.CONFLICT);
        }
        return response(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public static Mono<ServerResponse> response(Throwable error, HttpStatus status) {
        log.warn(LOG_ERROR, error.getMessage());
        return ServerResponse
                .status(status)
                .bodyValue(ApiError.builder()
                        .message(error.getMessage())
                        .reason(error.getLocalizedMessage())
                        .status(status)
                        .errors(List.of(error.getStackTrace()[0].toString(), error.getStackTrace()[1].toString()))
                        .build());
    }

    public static boolean isStatus400(Throwable error) {
        return error instanceof ConstraintViolationException ||
                error instanceof MethodArgumentNotValidException ||
                error instanceof BadRequestException;
    }

    public static boolean isStatus404(Throwable error) {
        return error instanceof CategoryNotFoundException ||
                error instanceof UserNotFoundException;
    }

    public static boolean isStatus409(Throwable error) {
        return error instanceof DataIntegrityViolationException ||
                error instanceof RequestConditionException ||
                error instanceof CategoryConditionException;
    }

//    @ExceptionHandler
//    public ResponseEntity<Map<String, String>> validationAnnotationHandler(MethodArgumentNotValidException error) {
//        log.warn(LOG_ERROR, error.getMessage());
//        return ResponseEntity.status(400).body(error.getFieldErrors().stream()
//                .collect(Collectors.toMap(FieldError::getField, Objects.requireNonNull(FieldError::getDefaultMessage))));
//    }
//

}
