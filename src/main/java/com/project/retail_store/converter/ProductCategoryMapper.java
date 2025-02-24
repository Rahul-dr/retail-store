package com.project.retail_store.converter;

import com.project.retail_store.dtos.ProductCategoryDto;
import com.project.retail_store.entity.ProductCategory;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductCategoryMapper {
    ProductCategoryMapper INSTANCE = Mappers.getMapper(ProductCategoryMapper.class);

    ProductCategoryDto toDto(ProductCategory category);
    ProductCategory toEntity(ProductCategoryDto categoryDto);
}
