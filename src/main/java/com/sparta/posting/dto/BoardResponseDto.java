package com.sparta.posting.dto;

import com.sparta.posting.entity.Board;
import lombok.Getter;

@Getter
public class BoardResponseDto {
    private String username;
    private String password;
    private String contents;

    public BoardResponseDto(Board board) {
        username = board.getUsername();
        password = board.getPassword();
        contents = board.getContents();
    }
}
