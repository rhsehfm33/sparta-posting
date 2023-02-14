package com.sparta.posting.service;

import com.sparta.posting.contant.ErrorMessage;
import com.sparta.posting.dto.ApiResponseData;
import com.sparta.posting.dto.CommentOuterResponseDto;
import com.sparta.posting.dto.CommentRequestDto;
import com.sparta.posting.entity.Board;
import com.sparta.posting.entity.Comment;
import com.sparta.posting.entity.User;
import com.sparta.posting.enums.ErrorType;
import com.sparta.posting.repository.BoardRepository;
import com.sparta.posting.repository.CommentRepository;
import com.sparta.posting.repository.UserRepository;
import com.sparta.posting.util.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final JwtUtil jwtUtil;

    @Transactional
    public CommentOuterResponseDto createComment(CommentRequestDto commentRequestDto, HttpServletRequest httpServletRequest) {
        String token = jwtUtil.resolveToken(httpServletRequest);
        if (jwtUtil.validateToken(token) == false) {
            throw new JwtException(ErrorMessage.WRONG_JWT_TOKEN);
        }

        // 토큰에서 사용자 정보 가져오기
        Claims claims = jwtUtil.getUserInfoFromToken(token);

        // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
        User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                () -> new EntityNotFoundException(ErrorMessage.USER_NOT_FOUND)
        );

        Board board = boardRepository.findById(commentRequestDto.getBoardId()).orElseThrow(
                () -> new EntityNotFoundException(ErrorMessage.BOARD_NOT_FOUND)
        );

        Comment newComment = new Comment(commentRequestDto, user, board);
        commentRepository.save(newComment);


        return new CommentOuterResponseDto(newComment);
    }

    @Transactional
    public CommentOuterResponseDto updateComment(Long commentId, CommentRequestDto commentRequestDto, HttpServletRequest httpServletRequest) {
        String token = jwtUtil.resolveToken(httpServletRequest);
        if (token == null || jwtUtil.validateToken(token) == false) {
            throw new JwtException(ErrorMessage.WRONG_JWT_TOKEN);
        }

        // 토큰에서 사용자 정보 가져오기
        Claims claims = jwtUtil.getUserInfoFromToken(token);

        // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
        User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                () -> new EntityNotFoundException(ErrorMessage.USER_NOT_FOUND)
        );

        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new EntityNotFoundException(ErrorMessage.COMMENT_NOT_FOUND)
        );

        if (comment.getUser() != user) {
            throw new IllegalArgumentException(ErrorMessage.AUTHORIZATION);
        }

        comment.update(commentRequestDto);

        return new CommentOuterResponseDto(comment);
    }

    @Transactional
    public ApiResponseData<CommentOuterResponseDto> deleteComment(Long commentId, HttpServletRequest httpServletRequest) {
        String token = jwtUtil.resolveToken(httpServletRequest);
        if (token == null || jwtUtil.validateToken(token) == false) {
            return new ApiResponseData(ErrorType.JWT_EXCEPTION, HttpStatus.PROXY_AUTHENTICATION_REQUIRED);
        }

        // 토큰에서 사용자 정보 가져오기
        Claims claims = jwtUtil.getUserInfoFromToken(token);

        // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
        User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                () -> new EntityNotFoundException(ErrorMessage.USER_NOT_FOUND)
        );

        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new EntityNotFoundException(ErrorMessage.COMMENT_NOT_FOUND)
        );

        if (comment.getUser() != user) {
            throw new IllegalArgumentException(ErrorMessage.AUTHORIZATION);
        }

        commentRepository.delete(comment);

        return new ApiResponseData(ErrorType.NONE, HttpStatus.OK);
    }
}
