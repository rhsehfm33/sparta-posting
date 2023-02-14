package com.sparta.posting.controller;

import com.sparta.posting.dto.ApiResponse;
import com.sparta.posting.dto.CommentOuterResponseDto;
import com.sparta.posting.dto.CommentRequestDto;
import com.sparta.posting.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comments")
public class CommentController {
    private final CommentService commentService;

    @PostMapping
    public ApiResponse<CommentOuterResponseDto> createComment(
            @RequestBody @Valid CommentRequestDto commentRequestDto,
            HttpServletRequest httpServletRequest
    ) {
       return commentService.createComment(commentRequestDto, httpServletRequest);
    }

    @PutMapping("/{commentId}")
    public ApiResponse<CommentOuterResponseDto> updateComment(
            @PathVariable Long commentId,
            @RequestBody @Valid CommentRequestDto commentRequestDto,
            HttpServletRequest httpServletRequest
    ) {
        return commentService.updateComment(commentId, commentRequestDto, httpServletRequest);
    }

    @DeleteMapping("/{commentId}")
    public ApiResponse deleteComment(
            @PathVariable Long commentId,
            HttpServletRequest httpServletRequest
    ) {
        return commentService.deleteComment(commentId, httpServletRequest);
    }
}
