package com.sparta.posting.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ErrorMessage {
    ERROR_NONE(""),
    ERROR_TOKEN_INVALID("Token is invalid");

    String description;

    ErrorMessage(String description) {
        this.description = description;
    }

    @JsonValue
    public String getMessage() {
        return this.description;
    }
}
