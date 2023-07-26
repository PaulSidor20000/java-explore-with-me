package ru.practicum.ewm.exceptions;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

@Slf4j
@RestControllerAdvice
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ErrorHandler {
    private static final String LOG_ERROR = "Error message: {}";
    private static final String DB_REASON = "Integrity constraint has been violated.";
    private static final String NOT_VALID_REASON = "Incorrectly made request.";

//    @ExceptionHandler
//    public ResponseEntity<ApiError> validationHandler(MethodArgumentNotValidException error) {
//        return ResponseEntity.status(400).body(response(error, HttpStatus.BAD_REQUEST));
//    }

    @ExceptionHandler({
            ConstraintViolationException.class,
            IllegalArgumentException.class,
            MethodArgumentNotValidException.class,
            EventConditionException.class,
            BadRequestException.class
    })
    public ResponseEntity<ApiError> validationHandler(Exception error) {
        return ResponseEntity.status(400).body(response(error, HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler({
            CategoryNotFoundException.class,
            UserNotFoundException.class,
            LocationNotFoundException.class,
            EventNotFoundException.class

    })
    public ResponseEntity<ApiError> validationEntityHandler(RuntimeException error) {
        log.warn(LOG_ERROR, error.getMessage());
        return ResponseEntity.status(404).body(response(error, HttpStatus.NOT_FOUND));
    }

    @ExceptionHandler({
            DataIntegrityViolationException.class,
            RequestConditionException.class,
            LocationConditionException.class,
            CategoryConditionException.class
    })
    public ResponseEntity<ApiError> validationEmailHandler(RuntimeException error) {
        return ResponseEntity.status(409).body(response(error, HttpStatus.CONFLICT));
    }

//    @ExceptionHandler
//    public ResponseEntity<ApiError> otherServerErrorsHandler(Throwable error) {
//        log.warn(LOG_ERROR, error.getMessage());
//        return ResponseEntity.status(500).body(response(error, HttpStatus.INTERNAL_SERVER_ERROR));
//    }

    private static ApiError response(Throwable error, HttpStatus status) {
        log.warn(LOG_ERROR, error.getMessage());

        return ApiError.builder()
                .message(error.getMessage())
                .reason(getReasonMessage(error))
                .status(getApiErrorStatus(error, status))
                .errors(getErrorList(error))
                .build();
    }

    private static List<String> getErrorList(Throwable error) {
        StringWriter out = new StringWriter();
        error.printStackTrace(new PrintWriter(out));

        return List.of(out.toString());
    }

    private static String getReasonMessage(Throwable error) {
        if (error instanceof DataIntegrityViolationException) {
            return DB_REASON;
        } else if (error instanceof MethodArgumentNotValidException ||
                error instanceof ConstraintViolationException) {
            return NOT_VALID_REASON;
        }
        return error.getLocalizedMessage();
    }

    private static HttpStatus getApiErrorStatus(Throwable error, HttpStatus status) {
        if (error instanceof EventConditionException ||
                error instanceof LocationConditionException ||
                error instanceof RequestConditionException) {
            return HttpStatus.FORBIDDEN;
        }
        return status;
    }

//    public static boolean isStatus409(Throwable error) {
//        return error instanceof DataIntegrityViolationException ||
//                error instanceof RequestConditionException ||
//                error instanceof LocationConditionException ||
//                error instanceof CategoryConditionException;
//    }

//    public static boolean isStatus400(Throwable error) {
//        return error instanceof ConstraintViolationException ||
//                error instanceof MethodArgumentNotValidException ||
//                error instanceof EventConditionException ||
//                error instanceof BadRequestException;

//    }
//    public static boolean isStatus404(Throwable error) {
//        return error instanceof CategoryNotFoundException ||
//                error instanceof UserNotFoundException ||
//                error instanceof LocationNotFoundException ||
//                error instanceof EventNotFoundException;

//    }
//    public static Mono<ServerResponse> handler(Throwable error) {
//        if (isStatus400(error)) {
//            return response(error, HttpStatus.BAD_REQUEST);
//        }
//        if (isStatus404(error)) {
//            return response(error, HttpStatus.NOT_FOUND);
//        }
//        if (isStatus409(error)) {
//            return response(error, HttpStatus.CONFLICT);
//        }
//        return response(error, HttpStatus.INTERNAL_SERVER_ERROR);

//    }

}
