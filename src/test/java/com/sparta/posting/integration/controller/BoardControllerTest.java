package com.sparta.posting.integration.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.posting.dto.BoardRequestDto;
import com.sparta.posting.dto.LoginRequestDto;
import com.sparta.posting.dto.SignupRequestDto;
import com.sparta.posting.enums.Category;
import com.sparta.posting.enums.UserRoleEnum;
import com.sparta.posting.jwt.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.core.Is.is;

@SpringBootTest
@AutoConfigureMockMvc

// IntelliJ's IDE's bug that can't configure @Autowired. So i suppress it.
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
public class BoardControllerTest {
    String token;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    ObjectMapper mapper;

    @Autowired
    MockMvc mvc;

    private static final String USER_URL = "/api/users";
    private static final String BOARD_URL = "/api/boards";

    @BeforeEach
    public void setUp() throws Exception {
        String body = mapper.writeValueAsString(
                new SignupRequestDto(
                        "test1",
                        "testtest1",
                        "test@email.com",
                        UserRoleEnum.ADMIN,
                        "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC"
                )
        );

        mvc.perform(post(USER_URL + "/signup")
                .contentType(MediaType.APPLICATION_JSON).characterEncoding("UTF-8")
                .content(body))
                .andReturn();

        body = mapper.writeValueAsString(
                new LoginRequestDto(
                        "test1",
                        "testtest1"
                )
        );
        MvcResult result = mvc.perform(post(USER_URL + "/login")
                .contentType(MediaType.APPLICATION_JSON).characterEncoding("UTF-8")
                .content(body))
                .andReturn();

        this.token = result.getResponse().getHeader("Authorization").substring(7);
    }

    @Test
    @DisplayName("Create Board success")
    void testBoardCreate() throws Exception {
        String body = mapper.writeValueAsString(
                new BoardRequestDto(Category.DIARY, "test")
        );

        mvc.perform(post(BOARD_URL)
                .header("Authorization", "Bearer " + this.token)
                .contentType(MediaType.APPLICATION_JSON).characterEncoding("UTF-8")
                .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.boardContent", is("test")));
    }
}
