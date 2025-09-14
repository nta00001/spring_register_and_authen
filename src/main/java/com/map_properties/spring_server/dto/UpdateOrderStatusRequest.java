package com.map_properties.spring_server.dto;

import com.map_properties.spring_server.enums.EOrderStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateOrderStatusRequest {
    @NotNull(message = "Trạng thái không được để trống")
    private EOrderStatus status;
}