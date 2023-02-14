package com.sparta.posting.dto;

import com.sparta.posting.entity.Board;
import com.sparta.posting.enums.Category;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class BoardWholeResponseDto {
    private UserOuterResponseDto user;
    private Category category;
    private String boardContent;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public BoardWholeResponseDto(Board board) {
        this.user = new UserOuterResponseDto(board.getUser());
        this.category = board.getCategory();
        this.boardContent = board.getBoardContent();
        this.createdAt = board.getCreatedAt();
        this.modifiedAt = board.getModifiedAt();
    }
}
