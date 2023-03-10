package com.sparta.posting.controller;

import com.sparta.posting.dto.ApiResponse;
import com.sparta.posting.dto.CommentOuterResponseDto;
import com.sparta.posting.dto.CommentRequestDto;
import com.sparta.posting.security.UserDetailsImpl;
import com.sparta.posting.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.nio.file.AccessDeniedException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comments")
public class CommentController {
    private final CommentService commentService;

    @PostMapping
    public ApiResponse<CommentOuterResponseDto> createComment(
            @RequestBody @Valid CommentRequestDto commentRequestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetailsImpl
    ) {
       return ApiResponse.successOf(HttpStatus.CREATED, commentService.createComment(commentRequestDto, userDetailsImpl));
    }

    @PutMapping("/{commentId}")
    public ApiResponse<CommentOuterResponseDto> updateComment(
            @PathVariable Long commentId,
            @RequestBody @Valid CommentRequestDto commentRequestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetailsImpl
    ) throws AccessDeniedException {
        return ApiResponse.successOf(HttpStatus.OK, commentService.updateComment(commentId, commentRequestDto, userDetailsImpl));
    }

    @DeleteMapping("/{commentId}")
    public ApiResponse<CommentOuterResponseDto> deleteComment(
            @PathVariable Long commentId,
            @AuthenticationPrincipal UserDetailsImpl userDetailsImpl
    ) throws AccessDeniedException {
        commentService.deleteComment(commentId, userDetailsImpl);
        return ApiResponse.successOf(HttpStatus.OK, null);
    }

    @PostMapping("/like/{commentId}")
    public ApiResponse<CommentOuterResponseDto> createComment(
            @PathVariable Long commentId,
            @AuthenticationPrincipal UserDetailsImpl userDetailsImpl
    ) throws AccessDeniedException {
        commentService.toggleCommentLike(commentId, userDetailsImpl);
        return ApiResponse.successOf(HttpStatus.OK, null);
    }
}
