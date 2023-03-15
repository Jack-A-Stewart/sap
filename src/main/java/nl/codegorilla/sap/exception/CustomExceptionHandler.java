package nl.codegorilla.sap.exception;

import nl.codegorilla.sap.model.ErrorDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(StudentNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleMyCustomException(StudentNotFoundException e) {
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), e.getMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CourseNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleMyCustomException(CourseNotFoundException e) {
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), e.getMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidFileException.class)
    public ResponseEntity<ErrorDetails> handleMyCustomException(InvalidFileException e) {
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), e.getMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ServerException.class)
    public ResponseEntity<ErrorDetails> handleMyCustomException(ServerException e) {
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), e.getMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

