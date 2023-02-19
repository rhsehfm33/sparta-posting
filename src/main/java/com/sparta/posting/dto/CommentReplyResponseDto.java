package com.sparta.posting.dto;

import com.sparta.posting.entity.Comment;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CommentReplyResponseDto {
    private long id;
    private String commentContent;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private List<CommentReplyResponseDto> commentOuterResponseDtoList = new ArrayList<>();
    private long likes;

    public CommentReplyResponseDto(Comment comment) {
        this.id = comment.getId();
        this.commentContent = comment.getCommentContent();
        this.createdAt = comment.getCreatedAt();
        this.modifiedAt = comment.getModifiedAt();
        this.likes = comment.getLikedCommentList().size();
        for (Comment reply : comment.getReplies()) {
            commentOuterResponseDtoList.add(
                    new CommentReplyResponseDto(reply)
            );
        }
    }
}
