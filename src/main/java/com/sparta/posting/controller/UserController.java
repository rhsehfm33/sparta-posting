package com.sparta.posting.controller;

import com.sparta.posting.dto.ApiResponse;
import com.sparta.posting.dto.LoginRequestDto;
import com.sparta.posting.dto.SignupRequestDto;
import com.sparta.posting.dto.UserOuterResponseDto;
import com.sparta.posting.entity.User;
import com.sparta.posting.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public ApiResponse<UserOuterResponseDto> signup(@RequestBody @Valid SignupRequestDto signupRequestDto) {
        return userService.signup(signupRequestDto);
    }

    @PostMapping("/login")
    public ApiResponse<UserOuterResponseDto> login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) {
        return userService.login(loginRequestDto, response);
    }
}
