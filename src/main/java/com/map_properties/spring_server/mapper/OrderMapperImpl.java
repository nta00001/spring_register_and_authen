package com.map_properties.spring_server.mapper;

import com.map_properties.spring_server.dto.OrderDTO;
import com.map_properties.spring_server.dto.OrderItemDTO;
import com.map_properties.spring_server.entity.Order;
import com.map_properties.spring_server.entity.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.stream.Collectors;

@Component
public class OrderMapperImpl implements OrderMapper {

    @Autowired
    private ProductMapper productMapper;

    @Override
    public OrderDTO toDTO(Order order) {
        if (order == null) return null;
        OrderDTO dto = new OrderDTO();
        dto.setId(order.getId());
        dto.setShippingAddress(order.getShippingAddress());
        dto.setTotalPrice(order.getTotalPrice());
        dto.setStatus(order.getStatus());
        dto.setCreatedAt(order.getCreatedAt());
        if (order.getUser() != null) {
            dto.setUserId(order.getUser().getId());
        }
        if (order.getItems() != null) {
            dto.setItems(order.getItems().stream().map(this::toDTO).collect(Collectors.toList()));
        }
        return dto;
    }

    @Override
    public OrderItemDTO toDTO(OrderItem orderItem) {
        if (orderItem == null) return null;
        OrderItemDTO dto = new OrderItemDTO();
        dto.setId(orderItem.getId());
        dto.setQuantity(orderItem.getQuantity());
        dto.setPrice(orderItem.getPrice());
        if (orderItem.getProduct() != null) {
            dto.setProduct(productMapper.toDTO(orderItem.getProduct()));
        }
        return dto;
    }
}