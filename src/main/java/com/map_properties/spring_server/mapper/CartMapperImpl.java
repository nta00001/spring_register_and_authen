package com.map_properties.spring_server.mapper;

import com.map_properties.spring_server.dto.CartDTO;
import com.map_properties.spring_server.dto.CartItemDTO;
import com.map_properties.spring_server.entity.Cart;
import com.map_properties.spring_server.entity.CartItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class CartMapperImpl implements CartMapper {

    @Autowired
    private ProductMapper productMapper;

    @Override
    public CartDTO toDTO(Cart cart) {
        if (cart == null) {
            return null;
        }

        CartDTO cartDTO = new CartDTO();
        cartDTO.setId(cart.getId());
        if (cart.getUser() != null) {
            cartDTO.setUserId(cart.getUser().getId());
        }
        if (cart.getItems() != null) {
            cartDTO.setItems(cart.getItems().stream()
                    .map(this::toDTO)
                    .collect(Collectors.toList()));
        }

        return cartDTO;
    }

    @Override
    public CartItemDTO toDTO(CartItem cartItem) {
        if (cartItem == null) {
            return null;
        }

        CartItemDTO cartItemDTO = new CartItemDTO();
        cartItemDTO.setId(cartItem.getId());
        cartItemDTO.setQuantity(cartItem.getQuantity());
        if (cartItem.getProduct() != null) {
            cartItemDTO.setProductId(cartItem.getProduct().getId());
            cartItemDTO.setProduct(productMapper.toDTO(cartItem.getProduct()));
        }

        return cartItemDTO;
    }
}