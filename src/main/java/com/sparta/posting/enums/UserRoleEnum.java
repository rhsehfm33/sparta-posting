package com.sparta.posting.enums;

public enum UserRoleEnum {
    USER("USER"),  // 사용자 권한
    ADMIN("ADMIN");  // 관리자 권한

    private final String authority;

    UserRoleEnum(String authority) {
        this.authority = authority;
    }

    public String getAuthority() {
        return this.authority;
    }
}