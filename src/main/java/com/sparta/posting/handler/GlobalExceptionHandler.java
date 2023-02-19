package com.sparta.posting.handler;

import com.sparta.posting.dto.ApiResponseDto;
import com.sparta.posting.dto.ErrorResponse;
import com.sparta.posting.enums.ErrorType;
import com.sparta.posting.util.ResponseEntityConverter;
import io.jsonwebtoken.JwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.persistence.EntityNotFoundException;
import javax.validation.ValidationException;
import java.nio.file.AccessDeniedException;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception e) {
        log.error(e.toString() + " occurred: {}", e.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(ErrorType.EXCEPTION, e.getMessage());
        return ResponseEntityConverter.convert(HttpStatus.INTERNAL_SERVER_ERROR, errorResponse);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleRuntimeException(RuntimeException e) {
        log.error(e.toString() + " occurred: {}", e.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(ErrorType.RUNTIME_EXCEPTION, e.getMessage());
        return ResponseEntityConverter.convert(HttpStatus.INTERNAL_SERVER_ERROR, errorResponse);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> handleEntityNotFoundException(EntityNotFoundException e) {
        log.error(e.toString() + " occurred: {}", e.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(ErrorType.ENTITY_NOT_FOUND_EXCEPTION, e.getMessage());
        return ResponseEntityConverter.convert(HttpStatus.BAD_REQUEST, errorResponse);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<?> handleValidationException(ValidationException e) {
        log.error(e.toString() + " occurred: {}", e.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(ErrorType.VALIDATION_EXCEPTION, e.getMessage());
        return ResponseEntityConverter.convert(HttpStatus.BAD_REQUEST, errorResponse);
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<?> handleJwtException(JwtException e) {
        log.error(e.toString() + " occurred: {}", e.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(ErrorType.JWT_EXCEPTION, e.getMessage());
        return ResponseEntityConverter.convert(HttpStatus.UNAUTHORIZED, errorResponse);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleIllegalArgumentException(IllegalArgumentException e) {
        log.error(e.toString() + " occurred: {}", e.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(ErrorType.ILLEGAL_ARGUMENT_EXCEPTION, e.getMessage());
        return ResponseEntityConverter.convert(HttpStatus.BAD_REQUEST, errorResponse);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> handleAccessDeniedException(AccessDeniedException e) {
        log.error(e.toString() + " occurred: {}", e.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(ErrorType.ACCESS_DENIED_EXCEPTION, e.getMessage());
        return ResponseEntityConverter.convert(HttpStatus.BAD_REQUEST, errorResponse);
    }
}
