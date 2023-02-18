package com.sparta.posting.controller;

import com.sparta.posting.dto.BoardRequestDto;
import com.sparta.posting.dto.BoardWholeResponseDto;
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
    @ResponseStatus(HttpStatus.CREATED)
    public BoardWholeResponseDto createBoard(
            @Valid BoardRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        return boardService.createBoard(requestDto, userDetails);
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
            @Valid BoardRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) throws AccessDeniedException {
        return boardService.update(boardId, requestDto, userDetails);
    }

    @DeleteMapping("/{boardId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteBoard(
            @PathVariable Long boardId,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) throws AccessDeniedException {
        boardService.deleteBoard(boardId, userDetails);
    }
}
