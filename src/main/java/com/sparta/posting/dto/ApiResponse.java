package com.sparta.posting.dto;

import com.sparta.posting.enums.ErrorMessage;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApiResponse<T> {
    private ErrorMessage errorMessage;
    private HttpStatus code;
    private T data;

    public ApiResponse(ErrorMessage errorMessage, HttpStatus httpStatus, T dto) {
        this.errorMessage = errorMessage;
        this.code = httpStatus;
        this.data = dto;
    }

    public ApiResponse(ErrorMessage errorMessage, HttpStatus httpStatus) {
        this.errorMessage = errorMessage;
        this.code = httpStatus;
    }
}
