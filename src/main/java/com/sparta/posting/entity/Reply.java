package com.sparta.posting.entity;

import com.sparta.posting.dto.ReplyRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class Reply extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String replyContent;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    private Comment comment;

    public Reply(String replyContent, User user, Board board, Comment comment) {
        this.replyContent = replyContent;
        this.user = user;
        this.board = board;
        this.comment = comment;
    }

    public void update(ReplyRequestDto replyRequestDto) {
        this.replyContent = replyRequestDto.getReplyContent();
    }
}
