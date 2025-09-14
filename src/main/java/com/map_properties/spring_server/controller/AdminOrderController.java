package com.map_properties.spring_server.controller;

import com.map_properties.spring_server.config.role.RequireRoles;
import com.map_properties.spring_server.dto.ListResponseDTO;
import com.map_properties.spring_server.dto.OrderDTO;
import com.map_properties.spring_server.dto.UpdateOrderStatusRequest;
import com.map_properties.spring_server.enums.ERole;
import com.map_properties.spring_server.request.FilterOrderRequest;
import com.map_properties.spring_server.service.AdminOrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/orders")
@CrossOrigin(origins = "*")
 // Bảo vệ tất cả các API trong controller này
public class AdminOrderController {

    @Autowired
    private AdminOrderService adminOrderService;

    @GetMapping("")
    @RequireRoles({ERole.ROLE_ADMIN})
    public ResponseEntity<ListResponseDTO<OrderDTO>> getAllOrders(@RequestBody(required = false) FilterOrderRequest request) {
        return ResponseEntity.ok(adminOrderService.getAllOrders(request));
    }

    @PatchMapping("/{orderId}/status")
    @RequireRoles({ERole.ROLE_ADMIN})
    public ResponseEntity<OrderDTO> updateOrderStatus(@PathVariable Long orderId, @Valid @RequestBody UpdateOrderStatusRequest request) {
        return ResponseEntity.ok(adminOrderService.updateOrderStatus(orderId, request));
    }
}