package com.map_properties.spring_server.service;

import com.map_properties.spring_server.dto.CartDTO;
import com.map_properties.spring_server.dto.CartItemDTO;

public interface CartService {
    CartDTO getCart();
    CartDTO addItemToCart(CartItemDTO cartItemDTO);
    CartDTO updateCartItem(Long itemId, int quantity);
    void removeItemFromCart(Long itemId);
    void clearCart();
}