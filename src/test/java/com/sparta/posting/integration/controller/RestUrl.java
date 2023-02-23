package com.sparta.posting.integration.controller;

public enum RestUrl {
    USER_URL("/api/users"),
    BOARD_URL("/api/boards");

    private String url;

    RestUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return this.url;
    }
}
