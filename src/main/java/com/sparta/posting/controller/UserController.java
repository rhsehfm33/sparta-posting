package com.sparta.posting.controller;

import com.sparta.posting.dto.LoginRequestDto;
import com.sparta.posting.dto.SignupRequestDto;
import com.sparta.posting.dto.UserOuterResponseDto;
import com.sparta.posting.service.UserService;
import com.sparta.posting.dto.ApiResponseData;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @ResponseBody
    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public void signup(@Valid SignupRequestDto signupRequestDto) {
        userService.signup(signupRequestDto);
    }

    @ResponseBody
    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public UserOuterResponseDto login(LoginRequestDto loginRequestDto, HttpServletResponse response) {
        return userService.login(loginRequestDto, response);
    }
}
