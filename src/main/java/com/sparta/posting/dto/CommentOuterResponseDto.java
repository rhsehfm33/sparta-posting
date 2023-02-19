package com.sparta.posting.dto;

import com.sparta.posting.entity.Comment;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CommentOuterResponseDto {
    private long id;
    private String commentContent;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private long likes;

    public CommentOuterResponseDto(Comment comment) {
        this.id = comment.getId();
        this.commentContent = comment.getCommentContent();
        this.createdAt = comment.getCreatedAt();
        this.modifiedAt = comment.getModifiedAt();
        this.likes = comment.getLikedCommentList().size();
    }
}
