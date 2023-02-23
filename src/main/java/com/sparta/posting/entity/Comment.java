package com.sparta.posting.entity;

import com.sparta.posting.dto.CommentRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Entity
@NoArgsConstructor
public class Comment extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String commentContent;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Board board;

    @OneToMany(mappedBy = "comment")
    private Set<Reply> replies = new HashSet<>();

    @OneToMany(mappedBy = "comment")
    private Set<CommentLike> likedCommentSet = new HashSet<>();


    public Comment(CommentRequestDto commentRequestDto, User user, Board board) {
        this.commentContent = commentRequestDto.getCommentContent();
        this.user = user;
        this.board = board;
    }

    public void update(CommentRequestDto commentRequestDto, User user) {
        this.commentContent = commentRequestDto.getCommentContent();
        this.user = user;
    }
}
