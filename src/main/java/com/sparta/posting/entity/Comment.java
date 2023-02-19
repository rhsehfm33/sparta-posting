package com.sparta.posting.entity;

import com.sparta.posting.dto.CommentRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Comment parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    private List<Comment> replies = new ArrayList<>();

    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL)
    private List<CommentLike> likedCommentList = new ArrayList<>();


    public Comment(CommentRequestDto commentRequestDto, User user, Board board, Comment parentComment) {
        this.parent = parentComment;
        this.commentContent = commentRequestDto.getCommentContent();
        this.user = user;
        this.board = board;
    }

    public void update(CommentRequestDto commentRequestDto, User user) {
        this.commentContent = commentRequestDto.getCommentContent();
        this.user = user;
    }
}
