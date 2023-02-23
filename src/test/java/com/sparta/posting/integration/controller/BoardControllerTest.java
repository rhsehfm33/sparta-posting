package com.sparta.posting.integration.controller;

import com.sparta.posting.dto.BoardRequestDto;
import com.sparta.posting.enums.Category;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.core.Is.is;

public class BoardControllerTest extends RestControllerTestWithToken {
    @Test
    @DisplayName("Create Board success")
    void testBoardCreate() throws Exception {
        String url = RestUrl.BOARD_URL.getUrl();
        BoardRequestDto dto = new BoardRequestDto(Category.DIARY, "test");

        MvcResult mvcResult = this.postMockOfUrlAndBody(url, dto)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.boardContent", is("test")))
                .andReturn();

        System.out.println(mvcResult);
    }
}
