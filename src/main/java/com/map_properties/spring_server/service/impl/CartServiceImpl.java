package com.map_properties.spring_server.service.impl;

import com.map_properties.spring_server.dto.CartDTO;
import com.map_properties.spring_server.dto.CartItemDTO;
import com.map_properties.spring_server.entity.Cart;
import com.map_properties.spring_server.entity.CartItem;
import com.map_properties.spring_server.entity.Product;
import com.map_properties.spring_server.entity.User;
import com.map_properties.spring_server.exception.ResourceNotFoundException; // Tạo exception này
import com.map_properties.spring_server.mapper.CartMapper;
import com.map_properties.spring_server.repository.CartItemRepository;
import com.map_properties.spring_server.repository.CartRepository;
import com.map_properties.spring_server.repository.ProductRepository;
import com.map_properties.spring_server.service.CartService;
import com.map_properties.spring_server.utils.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@Transactional
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartMapper cartMapper;

    private Cart getOrCreateCartForCurrentUser() {
        User currentUser = UserUtil.getUser();
        if (currentUser == null) {
            throw new IllegalStateException("Không tìm thấy người dùng đã đăng nhập.");
        }
        return cartRepository.findByUserId(currentUser.getId()).orElseGet(() -> {
            Cart newCart = new Cart();
            newCart.setUser(currentUser);
            return cartRepository.save(newCart);
        });
    }

    @Override
    public CartDTO getCart() {
        Cart cart = getOrCreateCartForCurrentUser();
        CartDTO cartDTO = cartMapper.toDTO(cart);
        cartDTO.setTotalPrice(calculateTotalPrice(cart));
        return cartDTO;
    }

    @Override
    public CartDTO addItemToCart(CartItemDTO cartItemDTO) {
        Cart cart = getOrCreateCartForCurrentUser();
        Product product = productRepository.findById(cartItemDTO.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy sản phẩm với ID: " + cartItemDTO.getProductId()));

        // Kiểm tra xem sản phẩm đã có trong giỏ hàng chưa
        CartItem existingItem = cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(product.getId()))
                .findFirst()
                .orElse(null);

        if (existingItem != null) {
            existingItem.setQuantity(existingItem.getQuantity() + cartItemDTO.getQuantity());
        } else {
            CartItem newItem = new CartItem();
            newItem.setProduct(product);
            newItem.setQuantity(cartItemDTO.getQuantity());
            cart.addItem(newItem);
        }

        cartRepository.save(cart);
        return getCart();
    }

    @Override
    public CartDTO updateCartItem(Long itemId, int quantity) {
        CartItem cartItem = cartItemRepository.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy mục trong giỏ hàng với ID: " + itemId));

        // TODO: Kiểm tra xem cartItem có thuộc về người dùng hiện tại không

        cartItem.setQuantity(quantity);
        cartItemRepository.save(cartItem);
        return getCart();
    }

    @Override
    public void removeItemFromCart(Long itemId) {
        // TODO: Kiểm tra xem cartItem có thuộc về người dùng hiện tại không
        cartItemRepository.deleteById(itemId);
    }

    @Override
    public void clearCart() {
        Cart cart = getOrCreateCartForCurrentUser();
        cart.getItems().clear();
        cartRepository.save(cart);
    }

    private BigDecimal calculateTotalPrice(Cart cart) {
        return cart.getItems().stream()
                .map(item -> item.getProduct().getPrice().multiply(new BigDecimal(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}