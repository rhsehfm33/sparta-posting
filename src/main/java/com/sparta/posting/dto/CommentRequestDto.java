package com.sparta.posting.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class CommentRequestDto {
    @Size(min = 1, max=200)
    private String commentContent;

    @NotNull
    private Long boardId;

    private Long parentCommentId;
}
