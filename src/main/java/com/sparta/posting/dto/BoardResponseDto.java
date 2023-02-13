package com.sparta.posting.dto;

import com.sparta.posting.entity.Board;
import com.sparta.posting.enums.Category;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class BoardResponseDto {
    private String username;
    private Category category;
    private String contents;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public BoardResponseDto(Board board) {
        this.username = board.getUser().getUsername();
        this.category = board.getCategory();
        this.contents = board.getContents();
        this.createdAt = board.getCreatedAt();
        this.modifiedAt = board.getModifiedAt();
    }
}
