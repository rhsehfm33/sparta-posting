package com.sparta.posting.dto;

import com.sparta.posting.enums.UserRoleEnum;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Setter
@Getter
public class SignupRequestDto {
    @Size(min=4, max=10)
    @Pattern(regexp="^[a-z0-9]+$")
    private String username;

    @Size(min=8, max=15)
    @Pattern(regexp="^[A-Za-z0-9]+$")
    private String password;

    @Email
    private String email;

    @NotNull
    private UserRoleEnum userRoleEnum;

    private String adminToken = "";
}
