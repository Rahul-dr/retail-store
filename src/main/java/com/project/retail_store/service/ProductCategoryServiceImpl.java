package com.project.retail_store.service;

import com.project.retail_store.converter.ProductCategoryMapper;
import com.project.retail_store.dtos.ProductCategoryDto;
import com.project.retail_store.entity.ProductCategory;
import com.project.retail_store.repository.ProductCategoryRepository;
import com.project.retail_store.service.interfaces.ProductCategoryService;
import com.project.retail_store.util.CommonUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductCategoryServiceImpl implements ProductCategoryService {
    private final ProductCategoryRepository categoryRepository;

    @Override
    @Transactional
    public ProductCategoryDto addCategory(ProductCategoryDto categoryDto) {
        ProductCategory category = ProductCategoryMapper.INSTANCE.toEntity(categoryDto);
        category.setDateCreated(LocalDateTime.now());
        category.setUserCreated("Admin"); // You can set this dynamically
        category.setActive(true);

        ProductCategory savedCategory = categoryRepository.save(category);
        return ProductCategoryMapper.INSTANCE.toDto(savedCategory);
    }

    @Override
    public List<ProductCategoryDto> getAllCategories() {
        List<ProductCategory> categories = categoryRepository.findByActiveTrue();
        return categories.stream()
                .map(ProductCategoryMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ProductCategoryDto updateCategory(Long categoryId, ProductCategoryDto categoryDto) {
        ProductCategory category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> CommonUtils.logAndGetException("Category not found with ID: " + categoryId));

        category.setName(categoryDto.getName() != null ? categoryDto.getName() : category.getName());
        category.setDescription(categoryDto.getDescription() != null ? categoryDto.getDescription() : category.getDescription());
        category.setDateModified(LocalDateTime.now());
        category.setUserModified("Admin"); // You can replace this with dynamic user info

        ProductCategory updatedCategory = categoryRepository.save(category);
        return ProductCategoryMapper.INSTANCE.toDto(updatedCategory);
    }
}
