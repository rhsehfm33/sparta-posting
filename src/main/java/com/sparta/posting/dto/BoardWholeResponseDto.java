package com.sparta.posting.dto;

import com.sparta.posting.entity.Board;
import com.sparta.posting.enums.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
public class BoardWholeResponseDto {
    private long id;
    private Category category;
    private String boardContent;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private long likes;
    private UserOuterResponseDto user;
    private List<CommentOuterResponseDto> commentList;


    BoardWholeResponseDto(Board board) {
        this.id = board.getId();
        this.category = board.getCategory();
        this.boardContent = board.getBoardContent();
        this.createdAt = board.getCreatedAt();
        this.modifiedAt = LocalDateTime.now();
        this.user = new UserOuterResponseDto(board.getUser());
        if (board.getCommentSet() != null) {
            this.commentList = board.getCommentSet().stream()
                    .map(comment -> new CommentOuterResponseDto(comment))
                    .collect(Collectors.toList());
        }
        this.likes = board.getCommentSet().size();
    }

    public static BoardWholeResponseDto of(Board board) {
        return new BoardWholeResponseDto(board);
    }
}
