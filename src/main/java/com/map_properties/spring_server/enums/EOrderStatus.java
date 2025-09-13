package com.map_properties.spring_server.enums;

public enum EOrderStatus {
    PENDING,      // Đang chờ xử lý
    PROCESSING,   // Đang xử lý
    SHIPPED,      // Đã giao hàng
    DELIVERED,    // Đã nhận hàng
    CANCELLED     // Đã hủy
}