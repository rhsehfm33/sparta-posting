package com.sparta.posting.dto;

import com.sparta.posting.enums.Category;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class BoardRequestDto {
    @NotBlank
    private Category category;

    @NotBlank
    private String contents;
}

