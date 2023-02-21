package com.sparta.posting.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class ReplyRequestDto {
    @Size(min = 1, max = 200)
    private String replyContent;

    @NotNull
    private Long boardId;

    @NotNull
    private Long commentId;

    private Long replyId;
}
