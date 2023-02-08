package com.sparta.posting.util;

import com.sparta.posting.enums.ErrorMessage;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApiResponse<T> {
    String error;
    HttpStatus code;
    T data;

    ApiResponse(String error, HttpStatus httpStatus, T dto) {
        this.error = error;
        this.code = httpStatus;
        this.data = dto;
    }
}
