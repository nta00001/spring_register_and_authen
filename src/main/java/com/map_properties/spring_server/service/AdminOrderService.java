package com.map_properties.spring_server.service;

import com.map_properties.spring_server.dto.ListResponseDTO;
import com.map_properties.spring_server.dto.OrderDTO;
import com.map_properties.spring_server.dto.UpdateOrderStatusRequest;
import com.map_properties.spring_server.request.FilterOrderRequest;

public interface AdminOrderService {
    ListResponseDTO<OrderDTO> getAllOrders(FilterOrderRequest request);
    OrderDTO updateOrderStatus(Long orderId, UpdateOrderStatusRequest request);
}