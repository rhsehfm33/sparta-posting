package com.sparta.posting.dto;

import com.sparta.posting.enums.ErrorType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponse {
    ErrorType errorType;
    String errorMessage;
    public ErrorResponse(ErrorType errorType, String errorMessage) {
        this.errorType = errorType;
        this.errorMessage = errorMessage;
    }
}
