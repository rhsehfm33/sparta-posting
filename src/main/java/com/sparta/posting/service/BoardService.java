package com.sparta.posting.service;

import com.sparta.posting.dto.BoardRequestDto;
import com.sparta.posting.dto.BoardResponseDto;
import com.sparta.posting.entity.Board;
import com.sparta.posting.mapper.BoardMapper;
import com.sparta.posting.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final BoardMapper boardMapper;

    @Transactional
    public BoardResponseDto createBoard(BoardRequestDto requestDto) {
        Board board = boardMapper.toEntity(requestDto);
        boardRepository.save(board);
        return boardMapper.toDto(board);
    }

    @Transactional
    public List<BoardResponseDto> getBoards() {
        List<Board> boardList = boardRepository.findAllByOrderByCreatedAtDesc();
        List<BoardResponseDto> boardResponseDtoList = boardList.stream()
                .map(board -> boardMapper.toDto(board))
                .collect(Collectors.toList());
        return boardResponseDtoList;
    }

    @Transactional
    public Long update(Long id, BoardRequestDto requestDto) {
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );

        board.setUsername(requestDto.getUsername());
        board.setContents(requestDto.getContents());
        board.setPassword(requestDto.getPassword());

        return board.getId();
    }

    @Transactional
    public Long deleteBoard(Long id) {
        boardRepository.deleteById(id);
        return id;
    }
}
