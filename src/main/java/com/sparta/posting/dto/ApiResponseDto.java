package com.sparta.posting.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class ApiResponseDto<T> {
    private int code;
    private ErrorResponse errorResponse = null;
    private T data = null;


    public ApiResponseDto(HttpStatus httpStatus, T data, ErrorResponse errorResponse) {
        this.code = httpStatus.value();
        this.data = data;
        this.errorResponse = errorResponse;
    }

    public ApiResponseDto(HttpStatus httpStatus, ErrorResponse errorResponse) {
        this.errorResponse = errorResponse;
        this.code = httpStatus.value();
    }

    public ApiResponseDto(HttpStatus httpStatus, T data) {
        this.data = data;
        this.code = httpStatus.value();
    }
}
