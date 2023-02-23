package com.sparta.posting.integration.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.posting.dto.LoginRequestDto;
import com.sparta.posting.dto.SignupRequestDto;
import com.sparta.posting.enums.UserRoleEnum;
import com.sparta.posting.jwt.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

// IntelliJ's IDE's bug that can't configure @Autowired. So i suppress it.
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@Disabled
public class RestControllerTestWithToken {
    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private MockMvc mvc;

    private String authToken;

    @BeforeEach
    protected void setUp() throws Exception {
        String body = mapper.writeValueAsString(
                new SignupRequestDto(
                        "test1",
                        "testtest1",
                        "test@email.com",
                        UserRoleEnum.ADMIN,
                        "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC"
                )
        );

        mvc.perform(post(RestUrl.USER_URL.getUrl() + "/signup")
                        .contentType(MediaType.APPLICATION_JSON).characterEncoding("UTF-8")
                        .content(body))
                .andReturn();

        body = mapper.writeValueAsString(
                new LoginRequestDto(
                        "test1",
                        "testtest1"
                )
        );
        MvcResult result = mvc.perform(post(RestUrl.USER_URL.getUrl() + "/login")
                        .contentType(MediaType.APPLICATION_JSON).characterEncoding("UTF-8")
                        .content(body))
                .andReturn();

        this.authToken = result.getResponse().getHeader("Authorization").substring(7);
    }

    protected <T> ResultActions postMockOfUrlAndBody(String url, T dto) throws Exception {
        String body = mapper.writeValueAsString(dto);
        return this.mvc.perform(post(url)
                .header("Authorization", "Bearer " + this.authToken)
                .contentType(MediaType.APPLICATION_JSON).characterEncoding("UTF-8")
                .content(body));
    }

    protected <T> ResultActions getMockOfUrlAndBody(String url, T dto) throws Exception {
        String body = mapper.writeValueAsString(dto);
        return this.mvc.perform(get(url)
                .header("Authorization", "Bearer " + this.authToken)
                .contentType(MediaType.APPLICATION_JSON).characterEncoding("UTF-8")
                .content(body));
    }

    protected <T> ResultActions putMockOfUrlAndBody(String url, T dto) throws Exception {
        String body = mapper.writeValueAsString(dto);
        return this.mvc.perform(put(url)
                .header("Authorization", "Bearer " + this.authToken)
                .contentType(MediaType.APPLICATION_JSON).characterEncoding("UTF-8")
                .content(body));
    }

    protected <T> ResultActions deleteMockOfUrlAndBody(String url, T dto) throws Exception {
        String body = mapper.writeValueAsString(dto);
        return this.mvc.perform(delete(url)
                .header("Authorization", "Bearer " + this.authToken)
                .contentType(MediaType.APPLICATION_JSON).characterEncoding("UTF-8")
                .content(body));
    }
}
