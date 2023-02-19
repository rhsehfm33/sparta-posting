package com.sparta.posting.controller;

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
    @ResponseStatus(HttpStatus.CREATED)
    public CommentOuterResponseDto createComment(
            @RequestBody @Valid CommentRequestDto commentRequestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
       return commentService.createComment(commentRequestDto, userDetails);
    }

    @PutMapping("/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    public CommentOuterResponseDto updateComment(
            @PathVariable Long commentId,
            @RequestBody @Valid CommentRequestDto commentRequestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) throws AccessDeniedException {
        return commentService.updateComment(commentId, commentRequestDto, userDetails);
    }

    @DeleteMapping("/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteComment(
            @PathVariable Long commentId,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) throws AccessDeniedException {
        commentService.deleteComment(commentId, userDetails);
    }

    @PostMapping("/like/{commentId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void createComment(
            @PathVariable Long commentId,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) throws AccessDeniedException {
        commentService.toggleCommentLike(commentId, userDetails);
    }
}
