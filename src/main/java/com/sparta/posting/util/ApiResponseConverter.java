package com.sparta.posting.util;

import com.sparta.posting.enums.ErrorMessage;
import org.springframework.http.HttpStatus;

public class ApiResponseConverter {
    public static <T> ApiResponse<T> convert(ErrorMessage errorMessage, HttpStatus httpStatus, T dto) {
        return new ApiResponse<T>(errorMessage.getMessage(), httpStatus, dto);
    }
    public static <T> ApiResponse<T> convert(ErrorMessage errorMessage, HttpStatus httpStatus) {
        return new ApiResponse<T>(errorMessage.getMessage(), httpStatus);
    }
}
