package com.sparta.posting.service;

import com.sparta.posting.dto.BoardRequestDto;
import com.sparta.posting.dto.BoardWholeResponseDto;
import com.sparta.posting.entity.Board;
import com.sparta.posting.entity.User;
import com.sparta.posting.enums.ErrorMessage;
import com.sparta.posting.repository.BoardRepository;
import com.sparta.posting.repository.UserRepository;
import com.sparta.posting.dto.ApiResponse;
import com.sparta.posting.util.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @Transactional
    public ApiResponse<BoardWholeResponseDto> createBoard(BoardRequestDto boardRequestDto, HttpServletRequest httpServletRequest) {
        String token = jwtUtil.resolveToken(httpServletRequest);
        if (token == null || jwtUtil.validateToken(token) == false) {
            return new ApiResponse(ErrorMessage.ERROR_TOKEN_INVALID, HttpStatus.PROXY_AUTHENTICATION_REQUIRED);
        }

        // 토큰에서 사용자 정보 가져오기
        Claims claims = jwtUtil.getUserInfoFromToken(token);

        // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
        User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
        );

        Board newBoard = new Board(boardRequestDto, user);
        boardRepository.save(newBoard);

        return new ApiResponse(ErrorMessage.ERROR_NONE, HttpStatus.CREATED, new BoardWholeResponseDto(newBoard));
    }

    @Transactional
    public ApiResponse<List<BoardWholeResponseDto>> getBoards() {
        List<Board> boardList = boardRepository.findAllByOrderByCreatedAtDesc();
        List<BoardWholeResponseDto> boardResponseDtoList = boardList.stream()
                .map(board -> new BoardWholeResponseDto(board))
                .collect(Collectors.toList());

        return new ApiResponse(ErrorMessage.ERROR_NONE, HttpStatus.OK, boardResponseDtoList);
    }

    @Transactional
    public ApiResponse update(Long id, BoardRequestDto boardRequestDto, HttpServletRequest httpServletRequest) {
        String token = jwtUtil.resolveToken(httpServletRequest);
        if (token == null || jwtUtil.validateToken(token) == false) {
            return new ApiResponse(ErrorMessage.ERROR_TOKEN_INVALID, HttpStatus.PROXY_AUTHENTICATION_REQUIRED);
        }

        // 토큰에서 사용자 정보 가져오기
        Claims claims = jwtUtil.getUserInfoFromToken(token);

        // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
        User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
        );

        Board board = boardRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("게시글이 존재하지 않습니다.")
        );

        board.update(boardRequestDto, user);

        return new ApiResponse(ErrorMessage.ERROR_NONE, HttpStatus.OK, new BoardWholeResponseDto(board));
    }

    @Transactional
    public ApiResponse deleteBoard(Long id, HttpServletRequest httpServletRequest) {
        String token = jwtUtil.resolveToken(httpServletRequest);
        if (token == null || jwtUtil.validateToken(token) == false) {
            return new ApiResponse(ErrorMessage.ERROR_TOKEN_INVALID, HttpStatus.PROXY_AUTHENTICATION_REQUIRED);
        }

        // 토큰에서 사용자 정보 가져오기
        Claims claims = jwtUtil.getUserInfoFromToken(token);

        // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
        User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
        );

        Board board = boardRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("게시글이 존재하지 않습니다.")
        );

        boardRepository.delete(board);

        return new ApiResponse(ErrorMessage.ERROR_NONE, HttpStatus.OK);
    }
}
