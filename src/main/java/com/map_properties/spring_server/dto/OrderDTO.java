package com.map_properties.spring_server.dto;

import com.map_properties.spring_server.enums.EOrderStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class OrderDTO {
    private Long id;
    private Long userId;
    private List<OrderItemDTO> items;
    private String shippingAddress;
    private BigDecimal totalPrice;
    private EOrderStatus status;
    private Date createdAt;
}