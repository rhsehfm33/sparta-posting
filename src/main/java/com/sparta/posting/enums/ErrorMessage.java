package com.sparta.posting.enums;

public enum ErrorMessage {
    ERROR_NONE(""),
    ERROR_TOKEN_INVALID("Token is invalid");

    String description;

    ErrorMessage(String description) {
        this.description = description;
    }

    public String getMessage() {
        return this.description;
    }
}
