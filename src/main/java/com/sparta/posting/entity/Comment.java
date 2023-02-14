package com.sparta.posting.entity;

import com.sparta.posting.dto.CommentRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class Comment extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String commentContent;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Board board;

    public Comment(CommentRequestDto commentRequestDto, User user, Board board) {
        this.commentContent = commentRequestDto.getCommentContent();
        this.user = user;
        this.board = board;
    }

    public void update(CommentRequestDto commentRequestDto) {
        this.commentContent = commentRequestDto.getCommentContent();
    }
}