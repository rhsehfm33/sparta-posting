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
    private String username;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private Category category;

    @Column(nullable = false)
    private String contents;

    public Board(BoardRequestDto boardRequestDto) {
        this.username = boardRequestDto.getUsername();
        this.password = boardRequestDto.getPassword();
        this.category = boardRequestDto.getCategory();
        this.contents = boardRequestDto.getContents();
    }

    public void update(BoardRequestDto boardRequestDto) {
        this.username = boardRequestDto.getUsername();
        this.contents = boardRequestDto.getContents();
        this.password = boardRequestDto.getPassword();
    }
}
