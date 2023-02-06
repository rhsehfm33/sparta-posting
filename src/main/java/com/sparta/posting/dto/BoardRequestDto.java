package com.sparta.posting.dto;

import lombok.Getter;

@Getter
public class BoardRequestDto {
    private String username;
    private String password;
    private String contents;
}

