package com.sparta.posting.controller;

import com.sparta.posting.dto.BoardRequestDto;
import com.sparta.posting.dto.BoardWholeResponseDto;
import com.sparta.posting.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.nio.file.AccessDeniedException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/boards")
public class BoardController {
    private final BoardService boardService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BoardWholeResponseDto createBoard(
            @RequestBody @Valid BoardRequestDto requestDto,
            HttpServletRequest httpServletRequest
    ) {
        return boardService.createBoard(requestDto, httpServletRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<BoardWholeResponseDto> getBoards() {
        return boardService.getBoards();
    }

    @PutMapping("/{boardId}")
    @ResponseStatus(HttpStatus.OK)
    public BoardWholeResponseDto updateBoard(
            @PathVariable Long boardId,
            @RequestBody @Valid BoardRequestDto requestDto,
            HttpServletRequest httpServletRequest
    ) throws AccessDeniedException {
        return boardService.update(boardId, requestDto, httpServletRequest);
    }

    @DeleteMapping("/{boardId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteBoard(
            @PathVariable Long boardId,
            HttpServletRequest httpServletRequest
    ) throws AccessDeniedException {
        boardService.deleteBoard(boardId, httpServletRequest);
    }
}
