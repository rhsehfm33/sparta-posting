package com.sparta.posting.dto;

import com.sparta.posting.entity.Board;
import com.sparta.posting.enums.Category;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class BoardWholeResponseDto {
    private long id;
    private Category category;
    private String boardContent;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private UserOuterResponseDto user;
    private List<CommentOuterResponseDto> commentList;
    private long likes;

    BoardWholeResponseDto(Board board) {
        this.id = board.getId();
        this.category = board.getCategory();
        this.boardContent = board.getBoardContent();
        this.createdAt = board.getCreatedAt();
        this.modifiedAt = LocalDateTime.now();
        this.user = new UserOuterResponseDto(board.getUser());
        if (board.getCommentList() != null) {
            this.commentList = board.getCommentList().stream()
                    .map(comment -> new CommentOuterResponseDto(comment))
                    .collect(Collectors.toList());
        }
        this.likes = board.getBoardLikeList().size();
    }

    public static BoardWholeResponseDto of(Board board) {
        return new BoardWholeResponseDto(board);
    }
}
