package com.sparta.posting.service;

import com.sparta.posting.dto.ApiResponse;
import com.sparta.posting.dto.LoginRequestDto;
import com.sparta.posting.dto.SignupRequestDto;
import com.sparta.posting.dto.UserOuterResponseDto;
import com.sparta.posting.entity.User;
import com.sparta.posting.enums.ErrorMessage;
import com.sparta.posting.enums.UserRoleEnum;
import com.sparta.posting.jwt.JwtUtil;
import com.sparta.posting.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private static final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

    @Transactional
    public UserOuterResponseDto signup(SignupRequestDto signupRequestDto) {
        String username = signupRequestDto.getUsername();
        String encodedPassword = passwordEncoder.encode(signupRequestDto.getPassword());

        // username 중복 확인
        Optional<User> found = userRepository.findByUsername(username);
        if (found.isPresent()) {
            throw new IllegalArgumentException(ErrorMessage.USERNAME_DUPLICATION.getMessage());
        }

        // 사용자 ROLE 확인
        if (signupRequestDto.getUserRoleEnum().equals(UserRoleEnum.ADMIN)) {
            if (signupRequestDto.getAdminToken() == null && !signupRequestDto.getAdminToken().equals(ADMIN_TOKEN)) {
                throw new EntityNotFoundException(ErrorMessage.WRONG_ADMIN_PASSWORD.getMessage());
            }
        }

        User newUser = new User(signupRequestDto, encodedPassword);
        userRepository.save(newUser);

        return UserOuterResponseDto.of(newUser);
    }

    @Transactional(readOnly = true)
    public UserOuterResponseDto login(LoginRequestDto loginRequestDto, HttpServletResponse response) {
        // 사용자 확인
        User user = userRepository.findByUsername(loginRequestDto.getUsername()).orElseThrow(
                () -> new EntityNotFoundException(ErrorMessage.WRONG_USERNAME.getMessage())
        );

        // 비밀번호 확인
        if (!user.getPassword().equals(loginRequestDto.getPassword())){
            throw  new EntityNotFoundException(ErrorMessage.WRONG_PASSWORD.getMessage());
        }

        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.getUsername(), user.getRole()));
        return UserOuterResponseDto.of(user);
    }
}
