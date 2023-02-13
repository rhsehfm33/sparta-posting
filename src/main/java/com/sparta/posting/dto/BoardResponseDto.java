package com.sparta.posting.dto;

import com.sparta.posting.entity.Board;
import com.sparta.posting.entity.User;
import com.sparta.posting.enums.Category;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardResponseDto {
    private User user;
    private Category category;
    private String contents;

    public BoardResponseDto(Board board) {
        this.user = board.getUser();
        this.category = board.getCategory();
        this.contents = board.getContents();
    }
}
