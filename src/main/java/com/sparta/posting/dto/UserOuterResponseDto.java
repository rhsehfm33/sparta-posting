package com.sparta.posting.dto;

import com.sparta.posting.entity.User;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UserOuterResponseDto {
    private String username;
    private String email;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public UserOuterResponseDto(User user) {
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.createdAt = user.getCreatedAt();
        this.modifiedAt = user.getModifiedAt();
    }
}
