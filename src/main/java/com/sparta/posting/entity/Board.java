package com.sparta.posting.entity;

import com.sparta.posting.dto.BoardRequestDto;
import com.sparta.posting.enums.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Board extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Category category;

    @Column(nullable = false)
    private String boardContent;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @OneToMany(mappedBy = "board")
    @OrderBy("createdAt DESC")
    private Set<Comment> commentSet = new HashSet<>();

    @OneToMany(mappedBy = "board")
    private Set<BoardLike> boardLikeSet = new HashSet<>();

    public Board(BoardRequestDto boardRequestDto, User user) {
        this.user = user;
        this.category = boardRequestDto.getCategory();
        this.boardContent = boardRequestDto.getContents();
    }

    public void update(BoardRequestDto boardRequestDto, User user) {
        this.user = user;
        this.category = boardRequestDto.getCategory();
        this.boardContent = boardRequestDto.getContents();
    }
}
