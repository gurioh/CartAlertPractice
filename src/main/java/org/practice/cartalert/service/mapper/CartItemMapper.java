package org.practice.cartalert.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.practice.cartalert.repository.entity.CartItem;
import org.practice.cartalert.service.dto.CartRequestDTO;

@Mapper(componentModel = "spring")
public interface CartItemMapper {
    CartItemMapper INSTANCE = Mappers.getMapper(CartItemMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "modifiedAt", expression = "java(java.time.LocalDateTime.now())")
    CartItem toEntity(CartRequestDTO dto);
}