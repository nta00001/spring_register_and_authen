package com.map_properties.spring_server.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequest {
    @NotEmpty(message = "Tên không được để trống")
    private String name;
    @Email(message = "Email không hợp lệ")
    @NotEmpty(message = "Email không được để trống")
    private String email;
    @NotEmpty(message = "Mật khẩu không được để trống")
    @Size(min = 8, message = "Mật khẩu phải có ít nhất 8 ký tự")
    private String password;

}
