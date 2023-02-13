package com.sparta.posting.controller;

import com.sparta.posting.dto.BoardRequestDto;
import com.sparta.posting.dto.BoardResponseDto;
import com.sparta.posting.service.BoardService;
import com.sparta.posting.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    @PostMapping("/api/boards")
    public ApiResponse<BoardResponseDto> createBoard(
            @RequestBody BoardRequestDto requestDto,
            HttpServletRequest httpServletRequest
    ) {
        return boardService.createBoard(requestDto, httpServletRequest);
    }

    @GetMapping("/api/boards")
    public ApiResponse<List<BoardResponseDto>> getBoards() {
        return boardService.getBoards();
    }

    @PutMapping("/api/boards/{id}")
    public ApiResponse updateBoard(
            @PathVariable Long id,
            @RequestBody BoardRequestDto requestDto,
            HttpServletRequest httpServletRequest
    ) {
        return boardService.update(id, requestDto, httpServletRequest);
    }

    @DeleteMapping("/api/boards/{id}")
    public ApiResponse deleteBoard(
            @PathVariable Long id,
            HttpServletRequest httpServletRequest
    ) {
        return boardService.deleteBoard(id, httpServletRequest);
    }
}
