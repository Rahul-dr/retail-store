package com.project.retail_store.converter;

import com.project.retail_store.dtos.SellerDto;
import com.project.retail_store.entity.Seller;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SellerMapper {
    SellerMapper INSTANCE = Mappers.getMapper(SellerMapper.class);
    
    SellerDto toDto(Seller seller);
    Seller toEntity(SellerDto sellerDto);
}