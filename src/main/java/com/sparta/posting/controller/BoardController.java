package com.sparta.posting.controller;

import com.sparta.posting.dto.BoardRequestDto;
import com.sparta.posting.dto.BoardWholeResponseDto;
import com.sparta.posting.service.BoardService;
import com.sparta.posting.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/boards")
public class BoardController {
    private final BoardService boardService;

    @PostMapping
    public ApiResponse<BoardWholeResponseDto> createBoard(
            @RequestBody @Valid BoardRequestDto requestDto,
            HttpServletRequest httpServletRequest
    ) {
        return boardService.createBoard(requestDto, httpServletRequest);
    }

    @GetMapping
    public ApiResponse<List<BoardWholeResponseDto>> getBoards() {
        return boardService.getBoards();
    }

    @PutMapping("/{boardId}")
    public ApiResponse updateBoard(
            @PathVariable Long boardId,
            @RequestBody @Valid BoardRequestDto requestDto,
            HttpServletRequest httpServletRequest
    ) {
        return boardService.update(boardId, requestDto, httpServletRequest);
    }

    @DeleteMapping("/{boardId}")
    public ApiResponse deleteBoard(
            @PathVariable Long boardId,
            HttpServletRequest httpServletRequest
    ) {
        return boardService.deleteBoard(boardId, httpServletRequest);
    }
}
