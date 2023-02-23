package com.sparta.posting.service;

import com.sparta.posting.dto.*;
import com.sparta.posting.entity.*;
import com.sparta.posting.enums.ErrorMessage;
import com.sparta.posting.enums.UserRoleEnum;
import com.sparta.posting.repository.*;
import com.sparta.posting.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    private final CommentRepository commentRepository;
    private final ReplyRepository replyRepository;

    @Transactional
    public ApiResponse<BoardOuterResponseDto> createBoard(BoardRequestDto boardRequestDto, UserDetailsImpl userDetails) {
        // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow(
                () -> new EntityNotFoundException(ErrorMessage.USER_NOT_FOUND.getMessage())
        );

        Board newBoard = new Board(boardRequestDto, user);
        boardRepository.save(newBoard);

        return ApiResponse.successOf(HttpStatus.CREATED, BoardOuterResponseDto.of(newBoard));
    }

    @Transactional
    public List<BoardWholeResponseDto> getBoards() {
        List<BoardWholeResponseDto> boardList = boardRepository.findAllByOrderByCreatedAtDesc();
        return boardList;
    }

    @Transactional
    public BoardWholeResponseDto getBoard(Long boardId) {
        BoardWholeResponseDto boardWholeResponseDto = boardRepository.findBoardWholeDtoByBoardId(boardId).orElseThrow(
                () -> new EntityNotFoundException(ErrorMessage.BOARD_NOT_FOUND.getMessage())
        );

        return boardWholeResponseDto;
    }

    @Transactional
    public ApiResponse<BoardOuterResponseDto> update(
            Long id,
            BoardRequestDto boardRequestDto,
            UserDetailsImpl userDetailsImpl
    ) throws AccessDeniedException {
        // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
        User user = userRepository.findByUsername(userDetailsImpl.getUsername()).orElseThrow(
                () -> new EntityNotFoundException(ErrorMessage.USER_NOT_FOUND.getMessage())
        );

        Board board = boardRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(ErrorMessage.BOARD_NOT_FOUND.getMessage())
        );

        if (user.getRole() != UserRoleEnum.ADMIN && board.getUser() != user) {
            throw new AccessDeniedException(ErrorMessage.ACCESS_DENIED.getMessage());
        }

        board.update(boardRequestDto, user);

        return ApiResponse.successOf(HttpStatus.OK, BoardOuterResponseDto.of(board));
    }

    @Transactional
    public ApiResponse<BoardOuterResponseDto> deleteBoard(
            Long id,
            UserDetailsImpl userDetailsImpl
    ) throws AccessDeniedException {
        // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
        User user = userRepository.findByUsername(userDetailsImpl.getUsername()).orElseThrow(
                () -> new EntityNotFoundException(ErrorMessage.USER_NOT_FOUND.getMessage())
        );

        Board board = boardRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(ErrorMessage.BOARD_NOT_FOUND.getMessage())
        );

        if (user.getRole() != UserRoleEnum.ADMIN && board.getUser() != user) {
            throw new AccessDeniedException(ErrorMessage.ACCESS_DENIED.getMessage());
        }

        replyRepository.deleteAllByBoard_Id(board.getId());

        commentRepository.deleteAllByBoard_Id(board.getId());

        boardRepository.delete(board);

        return ApiResponse.successOf(HttpStatus.OK, null);
    }

    @Transactional
    public ApiResponse<BoardOuterResponseDto> toggleBoardLike(Long boardId, UserDetailsImpl userDetailsImpl) throws AccessDeniedException {
        User user = userRepository.findByUsername(userDetailsImpl.getUsername()).orElseThrow(
                () -> new EntityNotFoundException(ErrorMessage.USER_NOT_FOUND.getMessage())
        );

        Board board = boardRepository.findById(boardId).orElseThrow(
                () -> new EntityNotFoundException(ErrorMessage.BOARD_NOT_FOUND.getMessage())
        );

        if (board.getUser() != user) {
            throw new AccessDeniedException(ErrorMessage.ACCESS_DENIED.getMessage());
        }

        BoardLike boardLike = boardLikeRepository.findByUserAndBoard(user, board).orElse(null);
        if (boardLike == null) {
            boardLikeRepository.save(new BoardLike(user, board));
        }
        else {
            boardLikeRepository.delete(boardLike);
        }

        return ApiResponse.successOf(HttpStatus.OK, BoardOuterResponseDto.of(board));
    }
}
