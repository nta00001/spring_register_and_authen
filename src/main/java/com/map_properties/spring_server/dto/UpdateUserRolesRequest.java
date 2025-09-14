package com.map_properties.spring_server.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import java.util.Set;

@Data
public class UpdateUserRolesRequest {
    @NotEmpty(message = "Danh sách vai trò không được để trống")
    private Set<String> roleCodes; // Admin sẽ gửi một danh sách các mã vai trò (ví dụ: ["admin", "user"])
}