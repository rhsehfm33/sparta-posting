package com.sparta.posting.mapper;

import com.sparta.posting.dto.BoardRequestDto;
import com.sparta.posting.dto.BoardResponseDto;
import com.sparta.posting.entity.Board;
import org.springframework.stereotype.Component;

@Component
public class BoardMapper {
    public BoardResponseDto toDto(Board board) {
        BoardResponseDto dto = new BoardResponseDto();
        dto.setUsername(board.getUsername());
        dto.setContents(board.getContents());
        return dto;
    }

    public Board toEntity(BoardRequestDto dto) {
        Board board = new Board();
        board.setUsername(dto.getUsername());
        board.setPassword(dto.getPassword());
        board.setContents(dto.getContents());
        return board;
    }
}
