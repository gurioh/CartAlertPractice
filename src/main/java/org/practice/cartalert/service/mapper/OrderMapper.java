package org.practice.cartalert.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.practice.cartalert.entity.CartItem;
import org.practice.cartalert.entity.Order;
import org.practice.cartalert.service.dto.CartRequestDTO;
import org.practice.cartalert.service.dto.OrderRequestDTO;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    Order toEntity(OrderRequestDTO dto);
}