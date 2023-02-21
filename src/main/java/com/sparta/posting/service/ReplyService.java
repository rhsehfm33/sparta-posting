package com.sparta.posting.service;

import com.sparta.posting.dto.ApiResponse;
import com.sparta.posting.dto.CommentRequestDto;
import com.sparta.posting.dto.ReplyOuterResponseDto;
import com.sparta.posting.dto.ReplyRequestDto;
import com.sparta.posting.entity.Board;
import com.sparta.posting.entity.Comment;
import com.sparta.posting.entity.Reply;
import com.sparta.posting.entity.User;
import com.sparta.posting.enums.ErrorMessage;
import com.sparta.posting.repository.BoardRepository;
import com.sparta.posting.repository.CommentRepository;
import com.sparta.posting.repository.ReplyRepository;
import com.sparta.posting.repository.UserRepository;
import com.sparta.posting.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReplyService {
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;
    private final ReplyRepository replyRepository;


    @Transactional
    public ApiResponse<ReplyOuterResponseDto> createReply(ReplyRequestDto replyRequestDto, UserDetailsImpl userDetailsImpl) {
        // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
        User user = userRepository.findByUsername(userDetailsImpl.getUsername()).orElseThrow(
                () -> new EntityNotFoundException(ErrorMessage.USER_NOT_FOUND.getMessage())
        );

        Board board = boardRepository.findById(replyRequestDto.getBoardId()).orElseThrow(
                () -> new EntityNotFoundException(ErrorMessage.BOARD_NOT_FOUND.getMessage())
        );

        Comment comment = commentRepository.findById(replyRequestDto.getCommentId()).orElseThrow(
                () -> new EntityNotFoundException(ErrorMessage.COMMENT_NOT_FOUND.getMessage())
        );

        Reply newReply = new Reply(replyRequestDto.getReplyContent(), user, board, comment);
        replyRepository.save(newReply);

        return ApiResponse.successOf(HttpStatus.OK, ReplyOuterResponseDto.of(newReply));
    }

    @Transactional
    public ApiResponse<List<ReplyOuterResponseDto>> getReplies(Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new EntityNotFoundException(ErrorMessage.COMMENT_NOT_FOUND.getMessage())
        );

        List<Reply> replyList = replyRepository.findAllByComment_Id(commentId);
        List<ReplyOuterResponseDto> replyOuterResponseDtoList = replyList.stream()
                .map(reply -> ReplyOuterResponseDto.of(reply))
                .collect(Collectors.toList());

        return ApiResponse.successOf(HttpStatus.OK, replyOuterResponseDtoList);
    }

    @Transactional
    public ApiResponse<ReplyOuterResponseDto> updateReply(ReplyRequestDto replyRequestDto, Long replyId, UserDetailsImpl userDetailsImpl) {
        Reply reply = replyRepository.findById(replyId).orElseThrow(
                () -> new EntityNotFoundException(ErrorMessage.REPLY_NOT_FOUND.getMessage())
        );

        reply.update(replyRequestDto);

        return ApiResponse.successOf(HttpStatus.OK, ReplyOuterResponseDto.of(reply));
    }

    @Transactional
    public ApiResponse<ReplyOuterResponseDto> deleteReply(ReplyRequestDto replyRequestDto, Long replyId, UserDetailsImpl userDetailsImpl) {
        Reply reply = replyRepository.findById(replyId).orElseThrow(
                () -> new EntityNotFoundException(ErrorMessage.REPLY_NOT_FOUND.getMessage())
        );

        replyRepository.delete(reply);

        return ApiResponse.successOf(HttpStatus.OK, null);
    }
}
