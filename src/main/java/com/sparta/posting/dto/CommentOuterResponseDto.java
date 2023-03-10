package com.sparta.posting.dto;

import com.sparta.posting.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class CommentOuterResponseDto {
    private long id;
    private String commentContent;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private long likes;

    CommentOuterResponseDto(Comment comment) {
        this.id = comment.getId();
        this.commentContent = comment.getCommentContent();
        this.createdAt = comment.getCreatedAt();
        this.modifiedAt = comment.getModifiedAt();
        this.likes = comment.getLikedCommentSet().size();
    }

    public static CommentOuterResponseDto of(Comment comment) {
        return new CommentOuterResponseDto(comment);
    }
}
