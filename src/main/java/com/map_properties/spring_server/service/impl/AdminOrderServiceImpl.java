package com.map_properties.spring_server.service.impl;

import com.map_properties.spring_server.dto.ListResponseDTO;
import com.map_properties.spring_server.dto.OrderDTO;
import com.map_properties.spring_server.dto.UpdateOrderStatusRequest;
import com.map_properties.spring_server.entity.Order;
import com.map_properties.spring_server.exception.ResourceNotFoundException;
import com.map_properties.spring_server.mapper.ListResponseMapper;
import com.map_properties.spring_server.mapper.OrderMapper;
import com.map_properties.spring_server.repository.OrderRepository;
import com.map_properties.spring_server.request.FilterOrderRequest;
import com.map_properties.spring_server.service.AdminOrderService;
import com.map_properties.spring_server.specification.OrderSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AdminOrderServiceImpl implements AdminOrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private ListResponseMapper<OrderDTO> listResponseMapper;

    @Override
    public ListResponseDTO<OrderDTO> getAllOrders(FilterOrderRequest request) {
        FilterOrderRequest filter = (request != null) ? request : new FilterOrderRequest();

        Specification<Order> spec = Specification.where(OrderSpecification.hasUserId(filter.getUserId()))
                .and(OrderSpecification.hasStatus(filter.getStatus()));

        Page<OrderDTO> pageOrders = orderRepository.findAll(spec, filter.toPageable())
                .map(order -> orderMapper.toDTO(order));

        return listResponseMapper.toDTO(pageOrders);
    }

    @Override
    public OrderDTO updateOrderStatus(Long orderId, UpdateOrderStatusRequest request) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy đơn hàng với ID: " + orderId));

        order.setStatus(request.getStatus());
        Order updatedOrder = orderRepository.save(order);

        // TODO: Cân nhắc gửi email thông báo cho người dùng khi trạng thái đơn hàng thay đổi

        return orderMapper.toDTO(updatedOrder);
    }
}