package com.sparta.posting.repository;

import com.sparta.posting.dto.BoardOuterResponseDto;
import com.sparta.posting.dto.BoardWholeResponseDto;
import com.sparta.posting.entity.Comment;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface BoardRepositoryCustom {
    public List<BoardWholeResponseDto> findAllByOrderByCreatedAtDesc();

    public Optional<BoardWholeResponseDto> findBoardWholeDtoByBoardId(Long boardId);
}
