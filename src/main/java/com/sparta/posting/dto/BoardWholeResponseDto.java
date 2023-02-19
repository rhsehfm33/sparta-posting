package com.sparta.posting.dto;

import com.sparta.posting.entity.Board;
import com.sparta.posting.entity.Comment;
import com.sparta.posting.enums.Category;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class BoardWholeResponseDto {
    private long id;
    private Category category;
    private String boardContent;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private UserOuterResponseDto user;
    private List<CommentReplyResponseDto> commentList = new ArrayList<>();
    private long likes;

    public BoardWholeResponseDto(Board board) {
        this.id = board.getId();
        this.category = board.getCategory();
        this.boardContent = board.getBoardContent();
        this.createdAt = board.getCreatedAt();
        this.modifiedAt = LocalDateTime.now();
        this.user = new UserOuterResponseDto(board.getUser());
        if (board.getCommentList().size() > 0) {
            for (Comment comment : board.getCommentList()) {
                if (comment.getParent() == null) {
                    this.commentList.add(new CommentReplyResponseDto(comment));
                }
            }
        }
        this.likes = board.getBoardLikeList().size();
    }
}
