package com.map_properties.spring_server.enums;

public enum ERole {
    ROLE_ADMIN("admin", "Quản trị hệ thống"),
    ROLE_USER("user", "Người dùng");

    private final String code;
    private final String name;

    ERole(String code) {
        this.code = code;
        this.name = code;
    }

    ERole(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
