package com.sparta.posting.service;

import com.sparta.posting.dto.BoardRequestDto;
import com.sparta.posting.dto.BoardResponseDto;
import com.sparta.posting.entity.Board;
import com.sparta.posting.enums.ErrorMessage;
import com.sparta.posting.repository.BoardRepository;
import com.sparta.posting.util.ApiResponse;
import com.sparta.posting.util.ApiResponseConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    @Transactional
    public ApiResponse<BoardResponseDto> createBoard(BoardRequestDto boardRequestDto) {
        Board board = new Board(boardRequestDto);
        boardRepository.save(board);
        return ApiResponseConverter.convert(
                ErrorMessage.ERROR_NONE, HttpStatus.CREATED, new BoardResponseDto(board)
        );
    }

    @Transactional
    public ApiResponse<List<BoardResponseDto>> getBoards() {
        List<Board> boardList = boardRepository.findAllByOrderByCreatedAtDesc();
        List<BoardResponseDto> boardResponseDtoList = boardList.stream()
                .map(board -> new BoardResponseDto(board))
                .collect(Collectors.toList());
        return ApiResponseConverter.convert(
                ErrorMessage.ERROR_NONE, HttpStatus.OK, boardResponseDtoList
        );
    }

    @Transactional
    public Long update(Long id, BoardRequestDto requestDto) {
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );

        board.update(requestDto);

        return board.getId();
    }

    @Transactional
    public Long deleteBoard(Long id) {
        boardRepository.deleteById(id);
        return id;
    }
}
