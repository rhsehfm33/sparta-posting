package com.sparta.posting.service;

import com.sparta.posting.dto.ApiResponse;
import com.sparta.posting.dto.CommentOuterResponseDto;
import com.sparta.posting.dto.CommentRequestDto;
import com.sparta.posting.entity.Board;
import com.sparta.posting.entity.Comment;
import com.sparta.posting.entity.User;
import com.sparta.posting.enums.ErrorMessage;
import com.sparta.posting.repository.BoardRepository;
import com.sparta.posting.repository.CommentRepository;
import com.sparta.posting.repository.UserRepository;
import com.sparta.posting.util.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final JwtUtil jwtUtil;

    @Transactional
    public ApiResponse<CommentOuterResponseDto> createComment(CommentRequestDto commentRequestDto, HttpServletRequest httpServletRequest) {
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

        Board board = boardRepository.findById(commentRequestDto.getBoardId()).orElseThrow(
                () -> new IllegalArgumentException("게시글이 존재하지 않습니다.")
        );

        Comment newComment = new Comment(commentRequestDto, user, board);
        commentRepository.save(newComment);


        return new ApiResponse(ErrorMessage.ERROR_NONE, HttpStatus.CREATED, new CommentOuterResponseDto(newComment));
    }

    @Transactional
    public ApiResponse<CommentOuterResponseDto> updateComment(Long commentId, CommentRequestDto commentRequestDto, HttpServletRequest httpServletRequest) {
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

        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new IllegalArgumentException("댓글이 존재하지 않습니다.")
        );

        if (comment.getUser() != user) {
            throw new IllegalArgumentException("해당 사용자는 댓글을 작성한 사용가 아닙니다.");
        }

        comment.update(commentRequestDto);

        return new ApiResponse(ErrorMessage.ERROR_NONE, HttpStatus.OK, new CommentOuterResponseDto(comment));
    }

    @Transactional
    public ApiResponse<CommentOuterResponseDto> deleteComment(Long commentId, HttpServletRequest httpServletRequest) {
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

        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new IllegalArgumentException("댓글이 존재하지 않습니다.")
        );

        if (comment.getUser() != user) {
            throw new IllegalArgumentException("해당 사용자는 댓글을 작성한 사용가 아닙니다.");
        }

        commentRepository.delete(comment);

        return new ApiResponse(ErrorMessage.ERROR_NONE, HttpStatus.OK);
    }
}
