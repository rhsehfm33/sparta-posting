package com.sparta.posting.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardRequestDto {
    private String username;
    private String password;
    private String contents;
}

