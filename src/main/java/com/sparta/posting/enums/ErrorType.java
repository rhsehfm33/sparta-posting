package com.sparta.posting.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ErrorType {
    NONE(""),
    EXCEPTION("Exception error"),
    RUNTIME_EXCEPTION("Runtime Exception error"),
    ENTITY_NOT_FOUND_EXCEPTION("entity not found error"),
    VALIDATION_EXCEPTION("validation fail error"),
    JWT_EXCEPTION("token invalid error"),
    ILLEGAL_ARGUMENT_EXCEPTION("wrong argument error");

    String description;

    ErrorType(String description) {
        this.description = description;
    }

    public String getMessage() {
        return this.description;
    }
}
