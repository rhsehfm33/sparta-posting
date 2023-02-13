package com.sparta.posting.dto;

import com.sparta.posting.entity.Board;
import com.sparta.posting.enums.Category;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardResponseDto {
    private String username;
    private Category category;
    private String contents;

    public BoardResponseDto(Board board) {
        this.username = board.getUsername();
        this.category = board.getCategory();
        this.contents = board.getContents();
    }
}
