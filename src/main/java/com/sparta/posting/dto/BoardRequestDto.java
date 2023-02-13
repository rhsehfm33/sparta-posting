package com.sparta.posting.dto;

import com.sparta.posting.enums.Category;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardRequestDto {
    private Category category;
    private String contents;
}

