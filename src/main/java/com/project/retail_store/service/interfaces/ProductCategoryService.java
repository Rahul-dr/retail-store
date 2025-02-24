package com.project.retail_store.service.interfaces;

import com.project.retail_store.dtos.ProductCategoryDto;
import java.util.List;

public interface ProductCategoryService {
    ProductCategoryDto addCategory(ProductCategoryDto categoryDto);
    List<ProductCategoryDto> getAllCategories();
    ProductCategoryDto updateCategory(Long categoryId, ProductCategoryDto categoryDto);
}
