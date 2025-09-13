package com.map_properties.spring_server.service.impl;

import com.map_properties.spring_server.dto.CheckoutRequestDTO;
import com.map_properties.spring_server.dto.OrderDTO;
import com.map_properties.spring_server.entity.*;
import com.map_properties.spring_server.enums.EOrderStatus;
import com.map_properties.spring_server.exception.ResourceNotFoundException;
import com.map_properties.spring_server.mapper.OrderMapper;
import com.map_properties.spring_server.repository.CartRepository;
import com.map_properties.spring_server.repository.OrderRepository;
import com.map_properties.spring_server.repository.ProductRepository;
import com.map_properties.spring_server.service.OrderService;
import com.map_properties.spring_server.utils.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public OrderDTO checkout(CheckoutRequestDTO checkoutRequest) {
        User currentUser = UserUtil.getUser();
        Cart cart = cartRepository.findByUserId(currentUser.getId())
                .orElseThrow(() -> new IllegalStateException("Không tìm thấy giỏ hàng cho người dùng."));

        if (cart.getItems().isEmpty()) {
            throw new IllegalStateException("Giỏ hàng trống.");
        }

        Order order = new Order();
        order.setUser(currentUser);
        order.setShippingAddress(checkoutRequest.getShippingAddress());
        order.setStatus(EOrderStatus.PENDING);

        BigDecimal totalPrice = BigDecimal.ZERO;
        for (CartItem cartItem : cart.getItems()) {
            Product product = cartItem.getProduct();

            // Kiểm tra số lượng tồn kho
            if (product.getStock() < cartItem.getQuantity()) {
                throw new IllegalStateException("Sản phẩm " + product.getName() + " không đủ số lượng tồn kho.");
            }

            // Giảm số lượng tồn kho
            product.setStock(product.getStock() - cartItem.getQuantity());
            productRepository.save(product);

            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(product);
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(product.getPrice()); // Lưu giá tại thời điểm mua
            order.addItem(orderItem);

            totalPrice = totalPrice.add(product.getPrice().multiply(new BigDecimal(cartItem.getQuantity())));
        }

        order.setTotalPrice(totalPrice);
        Order savedOrder = orderRepository.save(order);

        // Xóa giỏ hàng sau khi checkout
        cartRepository.delete(cart);

        return orderMapper.toDTO(savedOrder);
    }

    @Override
    public List<OrderDTO> getOrderHistory() {
        User currentUser = UserUtil.getUser();
        List<Order> orders = orderRepository.findByUserId(currentUser.getId());
        return orders.stream().map(orderMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public OrderDTO getOrderDetails(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy đơn hàng với ID: " + orderId));
        // TODO: Kiểm tra xem đơn hàng này có thuộc về người dùng đang đăng nhập không
        return orderMapper.toDTO(order);
    }
}