package com.map_properties.spring_server.mapper;

import com.map_properties.spring_server.dto.CartDTO;
import com.map_properties.spring_server.dto.CartItemDTO;
import com.map_properties.spring_server.entity.Cart;
import com.map_properties.spring_server.entity.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring", uses = {ProductMapper.class})
public interface CartMapper {

    @Mapping(source = "user.id", target = "userId")
    CartDTO toDTO(Cart cart);

    @Mapping(source = "product.id", target = "productId")
    CartItemDTO toDTO(CartItem cartItem);
}