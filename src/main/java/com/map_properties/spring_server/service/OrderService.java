package com.map_properties.spring_server.service;

import com.map_properties.spring_server.dto.CheckoutRequestDTO;
import com.map_properties.spring_server.dto.OrderDTO;
import java.util.List;

public interface OrderService {
    OrderDTO checkout(CheckoutRequestDTO checkoutRequest);
    List<OrderDTO> getOrderHistory();
    OrderDTO getOrderDetails(Long orderId);
}