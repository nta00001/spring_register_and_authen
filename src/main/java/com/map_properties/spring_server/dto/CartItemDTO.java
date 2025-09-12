package com.map_properties.spring_server.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CartItemDTO {
    private Long id;

    @NotNull(message = "Product ID không được để trống")
    private Long productId;

    private ProductDTO product; // Để hiển thị thông tin sản phẩm

    @NotNull(message = "Số lượng không được để trống")
    @Min(value = 1, message = "Số lượng phải lớn hơn 0")
    private int quantity;
}