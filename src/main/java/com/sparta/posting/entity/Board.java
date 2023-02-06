package com.sparta.posting.entity;

import com.sparta.posting.dto.BoardRequestDto;
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

    @Column(nullable = false)
    private String contents;

    public Board(BoardRequestDto boardRequestDto) {
        this.username = boardRequestDto.getUsername();
        this.password = boardRequestDto.getPassword();
        this.contents = boardRequestDto.getContents();
    }

    public void update(BoardRequestDto requestDto) {
        this.username = requestDto.getUsername();
        this.password = requestDto.getPassword();
        this.contents = requestDto.getContents();
    }
}
