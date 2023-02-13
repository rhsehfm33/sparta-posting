package com.sparta.posting.entity;

import com.sparta.posting.dto.SignupRequestDto;
import com.sparta.posting.enums.UserRoleEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Getter
@Entity(name = "users")
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String username;

    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<Board> boardList;

    public User(SignupRequestDto signupRequestDto) {
        this.username = signupRequestDto.getUsername();
        this.password = signupRequestDto.getPassword();
        this.email = signupRequestDto.getEmail();
        this.role = signupRequestDto.getUserRoleEnum();
    }
}
