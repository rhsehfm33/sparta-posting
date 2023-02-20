package com.sparta.posting.dto;

import com.sparta.posting.entity.Board;
import com.sparta.posting.enums.Category;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class BoardOuterResponseDto {
    private long id;
    private Category category;
    private String boardContent;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private long likes;

    BoardOuterResponseDto(Board board) {
        this.id = board.getId();
        this.category = board.getCategory();
        this.boardContent = board.getBoardContent();
        this.createdAt = board.getCreatedAt();
        this.modifiedAt = board.getModifiedAt();
        this.likes = board.getBoardLikeList().size();
    }

    public static BoardOuterResponseDto of(Board board) {
        return new BoardOuterResponseDto(board);
    }
}
