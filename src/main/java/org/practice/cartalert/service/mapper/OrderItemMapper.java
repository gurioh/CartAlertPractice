package org.practice.cartalert.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.practice.cartalert.entity.Order;
import org.practice.cartalert.entity.OrderItem;
import org.practice.cartalert.service.dto.CartRequestDTO;
import org.practice.cartalert.service.dto.OrderRequestDTO;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {
    OrderItemMapper INSTANCE = Mappers.getMapper(OrderItemMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    OrderItem toEntity(CartRequestDTO dto);
}