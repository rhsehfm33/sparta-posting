package com.sparta.posting.dto;

import com.sparta.posting.enums.ErrorType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@NoArgsConstructor
public class ApiResponseData<T> {
    private ErrorType errorType;
    private int code;
    private T data;

    public ApiResponseData(ErrorType errorType, HttpStatus httpStatus, T dto) {
        this.errorType = errorType;
        this.code = httpStatus.value();
        this.data = dto;
    }

    public ApiResponseData(ErrorType errorType, HttpStatus httpStatus) {
        this.errorType = errorType;
        this.code = httpStatus.value();
    }
}
