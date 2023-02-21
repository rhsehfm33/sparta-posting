package com.sparta.posting.handler;

import com.sparta.posting.dto.ApiResponse;
import com.sparta.posting.dto.ErrorResponseDto;
import com.sparta.posting.enums.ErrorType;
import io.jsonwebtoken.JwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.EntityNotFoundException;
import javax.validation.ValidationException;
import java.nio.file.AccessDeniedException;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<ErrorResponseDto>> handleException(Exception e) {
        log.error(e.toString() + " occurred: {}", e.getMessage());
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(ErrorType.EXCEPTION, e.getMessage());
        ApiResponse<ErrorResponseDto> errorResponseData = ApiResponse.failOf(HttpStatus.INTERNAL_SERVER_ERROR, errorResponseDto);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponseData);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponse<ErrorResponseDto>> handleRuntimeException(RuntimeException e) {
        log.error(e.toString() + " occurred: {}", e.getMessage());
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(ErrorType.RUNTIME_EXCEPTION, e.getMessage());
        ApiResponse<ErrorResponseDto> errorResponseData = ApiResponse.failOf(HttpStatus.INTERNAL_SERVER_ERROR, errorResponseDto);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponseData);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiResponse<ErrorResponseDto>> handleEntityNotFoundException(EntityNotFoundException e) {
        log.error(e.toString() + " occurred: {}", e.getMessage());
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(ErrorType.ENTITY_NOT_FOUND_EXCEPTION, e.getMessage());
        ApiResponse<ErrorResponseDto> errorResponseData = ApiResponse.failOf(HttpStatus.BAD_REQUEST, errorResponseDto);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponseData);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ApiResponse<ErrorResponseDto>> handleValidationException(ValidationException e) {
        log.error(e.toString() + " occurred: {}", e.getMessage());
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(ErrorType.VALIDATION_EXCEPTION, e.getMessage());
        ApiResponse<ErrorResponseDto> errorResponseData = ApiResponse.failOf(HttpStatus.INTERNAL_SERVER_ERROR, errorResponseDto);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponseData);
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<?> handleJwtException(JwtException e) {
        log.error(e.toString() + " occurred: {}", e.getMessage());
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(ErrorType.JWT_EXCEPTION, e.getMessage());
        ApiResponse<ErrorResponseDto> errorResponseData = ApiResponse.failOf(HttpStatus.INTERNAL_SERVER_ERROR, errorResponseDto);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponseData);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleIllegalArgumentException(IllegalArgumentException e) {
        log.error(e.toString() + " occurred: {}", e.getMessage());
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(ErrorType.ILLEGAL_ARGUMENT_EXCEPTION, e.getMessage());
        ApiResponse<ErrorResponseDto> errorResponseData = ApiResponse.failOf(HttpStatus.INTERNAL_SERVER_ERROR, errorResponseDto);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponseData);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> handleAccessDeniedException(AccessDeniedException e) {
        log.error(e.toString() + " occurred: {}", e.getMessage());
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(ErrorType.ACCESS_DENIED_EXCEPTION, e.getMessage());
        ApiResponse<ErrorResponseDto> errorResponseData = ApiResponse.failOf(HttpStatus.INTERNAL_SERVER_ERROR, errorResponseDto);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponseData);
    }
}
