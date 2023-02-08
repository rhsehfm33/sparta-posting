package com.sparta.posting.enums;

public enum ErrorMessage {
    ERROR_NONE("");

    String description;

    ErrorMessage(String description) {
        this.description = description;
    }

    public String getMessage() {
        return this.description;
    }
}
