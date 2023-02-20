package com.sparta.posting.service;

import com.sparta.posting.dto.ApiResponse;
import com.sparta.posting.dto.CommentOuterResponseDto;
import com.sparta.posting.dto.CommentRequestDto;
import com.sparta.posting.entity.Board;
import com.sparta.posting.entity.Comment;
import com.sparta.posting.entity.CommentLike;
import com.sparta.posting.entity.User;
import com.sparta.posting.enums.ErrorMessage;
import com.sparta.posting.enums.UserRoleEnum;
import com.sparta.posting.repository.BoardRepository;
import com.sparta.posting.repository.CommentLikeRepository;
import com.sparta.posting.repository.CommentRepository;
import com.sparta.posting.repository.UserRepository;
import com.sparta.posting.security.UserDetailsImpl;
import com.sparta.posting.util.ResponseEntityConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.nio.file.AccessDeniedException;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final CommentLikeRepository commentLikeRepository;

    @Transactional
    public ApiResponse<CommentOuterResponseDto> createComment(
            CommentRequestDto commentRequestDto,
            UserDetailsImpl userDetailsImpl
    ) {
        // 인증된 사용자 이름으로 사용자 정보를 DB에 조회
        User user = userRepository.findByUsername(userDetailsImpl.getUsername()).orElseThrow(
                () -> new EntityNotFoundException(ErrorMessage.USER_NOT_FOUND.getMessage())
        );

        Board board = boardRepository.findById(commentRequestDto.getBoardId()).orElseThrow(
                () -> new EntityNotFoundException(ErrorMessage.BOARD_NOT_FOUND.getMessage())
        );

        Comment parentComment = null;
        if (commentRequestDto.getParentCommentId() != null) {
            parentComment = commentRepository.findByIdAndBoard_Id(
                    commentRequestDto.getParentCommentId(),
                    commentRequestDto.getBoardId())
                    .orElseThrow(
                            () -> new EntityNotFoundException(ErrorMessage.COMMENT_NOT_FOUND.getMessage())
                    );
        }

        Comment newComment = new Comment(commentRequestDto, user, board, parentComment);
        commentRepository.save(newComment);

        return ApiResponse.successOf(HttpStatus.CREATED, CommentOuterResponseDto.of(newComment));
    }

    @Transactional
    public ApiResponse<CommentOuterResponseDto> updateComment(
            Long commentId,
            CommentRequestDto commentRequestDto,
            UserDetailsImpl userDetailsImpl
    ) throws AccessDeniedException {
        // 인증된 사용자 이름으로 사용자 정보를 DB에 조회
        User user = userRepository.findByUsername(userDetailsImpl.getUsername()).orElseThrow(
                () -> new EntityNotFoundException(ErrorMessage.USER_NOT_FOUND.getMessage())
        );

        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new EntityNotFoundException(ErrorMessage.COMMENT_NOT_FOUND.getMessage())
        );

        if (user.getRole() != UserRoleEnum.ADMIN && comment.getUser() != user) {
            throw new AccessDeniedException(ErrorMessage.ACCESS_DENIED.getMessage());
        }

        comment.update(commentRequestDto, user);

        return ApiResponse.successOf(HttpStatus.OK, CommentOuterResponseDto.of(comment));
    }

    @Transactional
    public ApiResponse<CommentOuterResponseDto> deleteComment(Long commentId, UserDetails userDetails) throws AccessDeniedException {
        // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow(
                () -> new EntityNotFoundException(ErrorMessage.USER_NOT_FOUND.getMessage())
        );

        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new EntityNotFoundException(ErrorMessage.COMMENT_NOT_FOUND.getMessage())
        );

        if (user.getRole() != UserRoleEnum.ADMIN && comment.getUser() != user) {
            throw new AccessDeniedException(ErrorMessage.ACCESS_DENIED.getMessage());
        }

        commentRepository.delete(comment);

        return ApiResponse.successOf(HttpStatus.OK, null);
    }

    @Transactional
    public ApiResponse<CommentOuterResponseDto> toggleCommentLike(Long commentId, UserDetailsImpl userDetails) throws AccessDeniedException {
        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow(
                () -> new EntityNotFoundException(ErrorMessage.USER_NOT_FOUND.getMessage())
        );

        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new EntityNotFoundException(ErrorMessage.COMMENT_NOT_FOUND.getMessage())
        );

        if (user.getRole() != UserRoleEnum.ADMIN && comment.getUser() != user) {
            throw new AccessDeniedException(ErrorMessage.ACCESS_DENIED.getMessage());
        }

        CommentLike commentLike = commentLikeRepository.findByUserAndComment(user, comment).orElse(null);
        if (commentLike == null) {
            commentLikeRepository.save(new CommentLike(user, comment));
        }
        else {
            commentLikeRepository.delete(commentLike);
        }

        return ApiResponse.successOf(HttpStatus.OK, null);
    }
}
