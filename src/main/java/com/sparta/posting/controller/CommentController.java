package com.sparta.posting.controller;

import com.sparta.posting.dto.ApiResponseData;
import com.sparta.posting.dto.CommentOuterResponseDto;
import com.sparta.posting.dto.CommentRequestDto;
import com.sparta.posting.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comments")
public class CommentController {
    private final CommentService commentService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CommentOuterResponseDto createComment(
            @RequestBody @Valid CommentRequestDto commentRequestDto,
            HttpServletRequest httpServletRequest
    ) {
       return commentService.createComment(commentRequestDto, httpServletRequest);
    }

    @PutMapping("/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    public CommentOuterResponseDto updateComment(
            @PathVariable Long commentId,
            @RequestBody @Valid CommentRequestDto commentRequestDto,
            HttpServletRequest httpServletRequest
    ) {
        return commentService.updateComment(commentId, commentRequestDto, httpServletRequest);
    }

    @DeleteMapping("/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseData deleteComment(
            @PathVariable Long commentId,
            HttpServletRequest httpServletRequest
    ) {
        return commentService.deleteComment(commentId, httpServletRequest);
    }
}
