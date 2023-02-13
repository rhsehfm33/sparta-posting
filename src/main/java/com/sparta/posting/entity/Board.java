package com.sparta.posting.entity;

import com.sparta.posting.dto.BoardRequestDto;
import com.sparta.posting.enums.Category;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class Board extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Category category;

    @Column(nullable = false)
    private String contents;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Board(BoardRequestDto boardRequestDto, User user) {
        this.user = user;
        this.category = boardRequestDto.getCategory();
        this.contents = boardRequestDto.getContents();
    }

    public void update(BoardRequestDto boardRequestDto, User user) {
        this.user = user;
        this.category = boardRequestDto.getCategory();
        this.contents = boardRequestDto.getContents();
    }
}
