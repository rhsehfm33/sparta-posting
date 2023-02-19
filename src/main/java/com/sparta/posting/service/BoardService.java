package com.sparta.posting.service;

import com.sparta.posting.dto.BoardRequestDto;
import com.sparta.posting.dto.BoardWholeResponseDto;
import com.sparta.posting.entity.*;
import com.sparta.posting.enums.ErrorMessage;
import com.sparta.posting.enums.UserRoleEnum;
import com.sparta.posting.repository.BoardLikeRepository;
import com.sparta.posting.repository.BoardRepository;
import com.sparta.posting.repository.UserRepository;
import com.sparta.posting.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final BoardLikeRepository boardLikeRepository;

    @Transactional
    public BoardWholeResponseDto createBoard(BoardRequestDto boardRequestDto, UserDetails userDetails) {
        // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow(
                () -> new EntityNotFoundException(ErrorMessage.USER_NOT_FOUND.getMessage())
        );

        Board newBoard = new Board(boardRequestDto, user);
        boardRepository.save(newBoard);

        return new BoardWholeResponseDto(newBoard);
    }

    @Transactional
    public List<BoardWholeResponseDto> getBoards() {
        List<Board> boardList = boardRepository.findAllByOrderByCreatedAtDesc();
        List<BoardWholeResponseDto> boardResponseDtoList = boardList.stream()
                .map(board -> new BoardWholeResponseDto(board))
                .collect(Collectors.toList());

        return boardResponseDtoList;
    }

    @Transactional
    public BoardWholeResponseDto update(
            Long id,
            BoardRequestDto boardRequestDto,
            UserDetails userDetails
    ) throws AccessDeniedException {
        // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow(
                () -> new EntityNotFoundException(ErrorMessage.USER_NOT_FOUND.getMessage())
        );

        Board board = boardRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(ErrorMessage.BOARD_NOT_FOUND.getMessage())
        );

        if (user.getRole() != UserRoleEnum.ADMIN && board.getUser() != user) {
            throw new AccessDeniedException(ErrorMessage.ACCESS_DENIED.getMessage());
        }

        board.update(boardRequestDto, user);

        return new BoardWholeResponseDto(board);
    }

    @Transactional
    public void deleteBoard(
            Long id,
            UserDetails userDetails
    ) throws AccessDeniedException {
        // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow(
                () -> new EntityNotFoundException(ErrorMessage.USER_NOT_FOUND.getMessage())
        );

        Board board = boardRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(ErrorMessage.BOARD_NOT_FOUND.getMessage())
        );

        if (user.getRole() != UserRoleEnum.ADMIN && board.getUser() != user) {
            throw new AccessDeniedException(ErrorMessage.ACCESS_DENIED.getMessage());
        }

        boardRepository.delete(board);
    }

    @Transactional
    public void toggleBoardLike(Long boardId, UserDetailsImpl userDetails) throws AccessDeniedException {
        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow(
                () -> new EntityNotFoundException(ErrorMessage.USER_NOT_FOUND.getMessage())
        );

        Board board = boardRepository.findById(boardId).orElseThrow(
                () -> new EntityNotFoundException(ErrorMessage.BOARD_NOT_FOUND.getMessage())
        );

        if (user.getRole() != UserRoleEnum.ADMIN && board.getUser() != user) {
            throw new AccessDeniedException(ErrorMessage.ACCESS_DENIED.getMessage());
        }

        BoardLike boardLike = boardLikeRepository.findByUserAndBoard(user, board).orElse(null);
        if (boardLike == null) {
            boardLikeRepository.save(new BoardLike(user, board));
        }
        else {
            boardLikeRepository.delete(boardLike);
        }
    }
}
