package com.project.retail_store.converter;

import com.project.retail_store.dtos.OrderDTO;
import com.project.retail_store.dtos.OrderItemDTO;
import com.project.retail_store.entity.Order;
import com.project.retail_store.entity.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(
        componentModel = "spring",  // Tell MapStruct to create a Spring bean
        imports = {Collectors.class}
)
public interface OrderMapper {
    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    @Mapping(source = "customer.id", target = "customerId")
    @Mapping(target = "orderItems", expression = "java(order.getOrderItems().stream().map(this::orderItemToDto).collect(Collectors.toList()))")
    OrderDTO toDto(Order order);

    @Mapping(target = "customer", ignore = true)
    @Mapping(target = "dateCreated", ignore = true)
    @Mapping(target = "userCreated", ignore = true)
    @Mapping(target = "dateModified", ignore = true)
    @Mapping(target = "userModified", ignore = true)
    Order toEntity(OrderDTO orderDto);

    @Mapping(source = "product.id", target = "productId")
    OrderItemDTO orderItemToDto(OrderItem orderItem);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "order", ignore = true)
    @Mapping(target = "product", ignore = true)
    OrderItem orderItemToEntity(OrderItemDTO orderItemDto);
}