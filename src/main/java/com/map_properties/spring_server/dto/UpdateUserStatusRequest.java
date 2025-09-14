package com.map_properties.spring_server.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateUserStatusRequest {
    @NotNull(message = "Trạng thái enabled không được để trống")
    private Boolean enabled;
}