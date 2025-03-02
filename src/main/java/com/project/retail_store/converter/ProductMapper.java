package com.project.retail_store.converter;

import com.project.retail_store.dtos.ProductDto;
import com.project.retail_store.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductMapper {
  ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

  @Mapping(target = "categoryId", source = "category.id") //
  @Mapping(target = "sellerId", source = "seller.id")
  ProductDto toDto(Product product);

  @Mapping(target = "category", ignore = true) //
  @Mapping(target = "seller", ignore = true)
  Product toEntity(ProductDto productDto);
}
