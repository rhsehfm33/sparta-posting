package com.sparta.posting.controller;

import com.sparta.posting.dto.ApiResponse;
import com.sparta.posting.dto.CommentOuterResponseDto;
import com.sparta.posting.dto.CommentRequestDto;
import com.sparta.posting.security.UserDetailsImpl;
import com.sparta.posting.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
       return commentService.createComment(commentRequestDto, userDetails);
    }

    @PutMapping("/{commentId}")
    public ApiResponse<CommentOuterResponseDto> updateComment(
            @PathVariable Long commentId,
            @RequestBody @Valid CommentRequestDto commentRequestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) throws AccessDeniedException {
        return commentService.updateComment(commentId, commentRequestDto, userDetails);
    }

    @DeleteMapping("/{commentId}")
    public ApiResponse<CommentOuterResponseDto> deleteComment(
            @PathVariable Long commentId,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) throws AccessDeniedException {
        return commentService.deleteComment(commentId, userDetails);
    }

    @PostMapping("/like/{commentId}")
    public ApiResponse<CommentOuterResponseDto> createComment(
            @PathVariable Long commentId,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) throws AccessDeniedException {
        return commentService.toggleCommentLike(commentId, userDetails);
    }
}
