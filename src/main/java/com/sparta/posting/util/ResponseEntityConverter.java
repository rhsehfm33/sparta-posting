package com.sparta.posting.util;

import com.sparta.posting.dto.ApiResponseDto;
import com.sparta.posting.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseEntityConverter {
    public static<T> ResponseEntity<?> convert(HttpStatus httpStatus, T dto, ErrorResponse errorResponse) {
        return new ResponseEntity(new ApiResponseDto(httpStatus, dto, errorResponse), httpStatus);
    }

    public static ResponseEntity<?> convert(HttpStatus httpStatus, ErrorResponse errorResponse) {
        return new ResponseEntity(new ApiResponseDto(httpStatus, errorResponse), httpStatus);
    }

    public static<T> ResponseEntity<?> convert(HttpStatus httpStatus, T dto) {
        return new ResponseEntity(new ApiResponseDto(httpStatus, dto), httpStatus);
    }
}
