package com.map_properties.spring_server.controller;

import com.map_properties.spring_server.dto.CheckoutRequestDTO;
import com.map_properties.spring_server.dto.OrderDTO;
import com.map_properties.spring_server.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@CrossOrigin(origins = "*")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/checkout")
    public ResponseEntity<OrderDTO> checkout(@Valid @RequestBody CheckoutRequestDTO checkoutRequest) {
        return ResponseEntity.ok(orderService.checkout(checkoutRequest));
    }

    @GetMapping("")
    public ResponseEntity<List<OrderDTO>> getOrderHistory() {
        return ResponseEntity.ok(orderService.getOrderHistory());
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDTO> getOrderDetails(@PathVariable Long orderId) {
        return ResponseEntity.ok(orderService.getOrderDetails(orderId));
    }
}