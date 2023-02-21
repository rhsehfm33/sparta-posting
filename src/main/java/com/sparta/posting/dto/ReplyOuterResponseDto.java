package com.sparta.posting.dto;

import com.sparta.posting.entity.Comment;
import com.sparta.posting.entity.Reply;

import java.time.LocalDateTime;

public class ReplyOuterResponseDto {
    private long id;
    private String replyContent;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    ReplyOuterResponseDto(Reply reply) {
        this.id = reply.getId();
        this.replyContent = reply.getReplyContent();
        this.createdAt = reply.getCreatedAt();
        this.modifiedAt = reply.getModifiedAt();
    }

    public static ReplyOuterResponseDto of(Reply reply) {
        return new ReplyOuterResponseDto(reply);
    }
}
