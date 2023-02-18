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

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Board> boardList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Comment> commentList = new ArrayList<>();

    public User(SignupRequestDto signupRequestDto) {
        this.username = signupRequestDto.getUsername();
        this.password = signupRequestDto.getPassword();
        this.email = signupRequestDto.getEmail();
        this.role = signupRequestDto.getUserRoleEnum();
    }
}
