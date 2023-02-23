package com.sparta.posting.dto;

import com.sparta.posting.enums.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
public class BoardRequestDto {
    @NotNull
    private Category category;

    @NotBlank
    private String contents;
}

