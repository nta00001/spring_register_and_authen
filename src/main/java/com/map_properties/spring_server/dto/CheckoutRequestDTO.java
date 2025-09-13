package com.map_properties.spring_server.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class CheckoutRequestDTO {
    @NotEmpty(message = "Địa chỉ giao hàng không được để trống")
    private String shippingAddress;
}