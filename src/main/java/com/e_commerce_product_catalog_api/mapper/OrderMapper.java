package com.e_commerce_product_catalog_api.mapper;

import com.e_commerce_product_catalog_api.dtos.OrderDTO;
import com.e_commerce_product_catalog_api.dtos.OrderItemDTO;
import com.e_commerce_product_catalog_api.entity.Order;
import com.e_commerce_product_catalog_api.entity.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Mapper (componentModel = "spring", uses = {ProductMapper.class})
@Component
public interface OrderMapper {

    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    @Mapping(source = "orderItems", target = "orderItems")
    OrderDTO toDTO(Order order);

    @Mapping(target = "orderItems", ignore = true)
    Order toEntity(OrderDTO orderDTO);

    @Mapping(source = "product", target = "product")
    OrderItemDTO toOrderItemDTO(OrderItem orderItem);

    OrderItem toOrderItem(OrderItemDTO orderItemDTO);
}

