package com.map_properties.spring_server.mapper;

import com.map_properties.spring_server.dto.OrderDTO;
import com.map_properties.spring_server.dto.OrderItemDTO;
import com.map_properties.spring_server.entity.Order;
import com.map_properties.spring_server.entity.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring", uses = {ProductMapper.class})
public interface OrderMapper {

    @Mapping(source = "user.id", target = "userId")
    OrderDTO toDTO(Order order);

    OrderItemDTO toDTO(OrderItem orderItem);
}