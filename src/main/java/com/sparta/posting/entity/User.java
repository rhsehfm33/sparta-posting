package com.sparta.posting.entity;

import com.sparta.posting.dto.SignupRequestDto;
import com.sparta.posting.enums.UserRoleEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity(name = "users")
@NoArgsConstructor
public class User extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;

    public User(SignupRequestDto signupRequestDto, String encodedPassword) {
        this.username = signupRequestDto.getUsername();
        this.password = encodedPassword;
        this.email = signupRequestDto.getEmail();
        this.role = signupRequestDto.getUserRoleEnum();
    }
}
