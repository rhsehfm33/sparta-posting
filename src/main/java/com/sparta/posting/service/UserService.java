package com.sparta.posting.service;

import com.sparta.posting.dto.LoginRequestDto;
import com.sparta.posting.dto.SignupRequestDto;
import com.sparta.posting.entity.User;
import com.sparta.posting.enums.ErrorMessage;
import com.sparta.posting.enums.UserRoleEnum;
import com.sparta.posting.dto.ApiResponse;
import com.sparta.posting.util.JwtUtil;
import com.sparta.posting.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private static final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

    @Transactional
    public ApiResponse<User> signup(SignupRequestDto signupRequestDto) {
        // username 중복 확인
        Optional<User> found = userRepository.findByUsername(signupRequestDto.getUsername());
        if (found.isPresent()) {
            throw new IllegalArgumentException("username이 중복됐습니다.");
        }

        // 사용자 ROLE 확인
        if (signupRequestDto.getUserRoleEnum().equals(UserRoleEnum.ADMIN)) {
            if (!signupRequestDto.getAdminToken().equals(ADMIN_TOKEN)) {
                throw new IllegalArgumentException("관리자 암호가 틀려 등록이 불가능합니다.");
            }
        }

        User newUser = new User(signupRequestDto);
        userRepository.save(newUser);
        return new ApiResponse(ErrorMessage.ERROR_NONE, HttpStatus.CREATED);
    }

    @Transactional(readOnly = true)
    public ApiResponse<User> login(LoginRequestDto loginRequestDto, HttpServletResponse response) {
        // 사용자 확인
        User user = userRepository.findByUsername(loginRequestDto.getUsername()).orElseThrow(
                () -> new IllegalArgumentException("username이 일치하지 않습니다.")
        );

        // 비밀번호 확인
        if(!user.getPassword().equals(loginRequestDto.getPassword())){
            throw  new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.getUsername(), user.getRole()));
        return new ApiResponse(ErrorMessage.ERROR_NONE, HttpStatus.OK);
    }
}
