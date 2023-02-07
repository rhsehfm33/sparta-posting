package com.sparta.posting.mapper;

import com.sparta.posting.dto.BoardRequestDto;
import com.sparta.posting.dto.BoardResponseDto;
import com.sparta.posting.entity.Board;
import org.springframework.stereotype.Component;
import org.springframework.http.ResponseEntity;

@Component
public class BoardMapper implements Mappable<BoardRequestDto, Board, BoardResponseDto> {
    @Override
    public Board toEntity(BoardRequestDto dto) {
        Board board = new Board();
        board.setUsername(dto.getUsername());
        board.setPassword(dto.getPassword());
        board.setCategory(dto.getCategory());
        board.setContents(dto.getContents());
        return board;
    }

    @Override
    public BoardResponseDto toDto(Board board) {
        BoardResponseDto dto = new BoardResponseDto();
        dto.setUsername(board.getUsername());
        dto.setCategory(board.getCategory());
        dto.setContents(board.getContents());
        return dto;
    }
}
