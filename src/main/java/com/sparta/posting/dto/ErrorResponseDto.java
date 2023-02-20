package com.sparta.posting.dto;

import com.sparta.posting.enums.ErrorType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponseDto {
    ErrorType errorType;
    String errorMessage;
    public ErrorResponseDto(ErrorType errorType, String errorMessage) {
        this.errorType = errorType;
        this.errorMessage = errorMessage;
    }
}
