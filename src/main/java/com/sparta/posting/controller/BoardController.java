package com.sparta.posting.controller;

import com.sparta.posting.dto.*;
import com.sparta.posting.security.UserDetailsImpl;
import com.sparta.posting.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.nio.file.AccessDeniedException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/boards")
public class BoardController {
    private final BoardService boardService;

    @PostMapping
    public ApiResponse<BoardOuterResponseDto> createBoard(
            @RequestBody @Valid BoardRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        return ApiResponse.successOf(HttpStatus.CREATED, boardService.createBoard(requestDto, userDetails));
    }

    @GetMapping
    public ApiResponse<List<BoardWholeResponseDto>> getBoards() {
        return ApiResponse.successOf(HttpStatus.OK, boardService.getBoards());
    }

    @GetMapping("/{boardId}")
    public ApiResponse<BoardWholeResponseDto> getBoard(@PathVariable Long boardId) {
        return ApiResponse.successOf(HttpStatus.OK, boardService.getBoard(boardId));
    }

    @PutMapping("/{boardId}")
    public ApiResponse<BoardOuterResponseDto> updateBoard(
            @PathVariable Long boardId,
            @RequestBody @Valid BoardRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) throws AccessDeniedException {
        return ApiResponse.successOf(HttpStatus.OK, boardService.update(boardId, requestDto, userDetails));
    }

    @DeleteMapping("/{boardId}")
    public ApiResponse<BoardOuterResponseDto> deleteBoard(
            @PathVariable Long boardId,
            @AuthenticationPrincipal UserDetailsImpl userDetailsImpl
    ) throws AccessDeniedException {
        boardService.deleteBoard(boardId, userDetailsImpl);
        return ApiResponse.successOf(HttpStatus.OK, null);
    }

    @PostMapping("/like/{boardId}")
    public ApiResponse<BoardOuterResponseDto> toggleLikeBoard(
            @PathVariable Long boardId,
            @AuthenticationPrincipal UserDetailsImpl userDetailsImpl
    ) throws AccessDeniedException {
        boardService.toggleBoardLike(boardId, userDetailsImpl);
        return ApiResponse.successOf(HttpStatus.OK, boardService.toggleBoardLike(boardId, userDetailsImpl));
    }
}
