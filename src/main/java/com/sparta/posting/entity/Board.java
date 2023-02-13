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

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private Category category;

    @Column(nullable = false)
    private String contents;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Board(BoardRequestDto boardRequestDto) {
        this.password = boardRequestDto.getPassword();
        this.category = boardRequestDto.getCategory();
        this.contents = boardRequestDto.getContents();
    }

    public void update(BoardRequestDto boardRequestDto) {
        this.password = boardRequestDto.getPassword();
        this.category = boardRequestDto.getCategory();
        this.contents = boardRequestDto.getContents();
    }
}
