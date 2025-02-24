package com.project.retail_store.controller;

import com.project.retail_store.dtos.ProductCategoryDto;
import com.project.retail_store.service.interfaces.ProductCategoryService;
import com.project.retail_store.dtos.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/categories")
@RequiredArgsConstructor
public class ProductCategoryController {
    private final ProductCategoryService productCategoryService;

    @PostMapping("/add")
    public ApiResponse<ProductCategoryDto> addCategory(@RequestBody ProductCategoryDto categoryDto) {
        return new ApiResponse<>("Category added successfully", productCategoryService.addCategory(categoryDto));
    }

    @GetMapping
    public ApiResponse<List<ProductCategoryDto>> getAllCategories() {
        return new ApiResponse<>("Categories retrieved successfully", productCategoryService.getAllCategories());
    }

    @PutMapping("/update/{categoryId}")
    public ApiResponse<ProductCategoryDto> updateCategory(@PathVariable Long categoryId, @RequestBody ProductCategoryDto categoryDto) {
        return new ApiResponse<>("Category updated successfully", productCategoryService.updateCategory(categoryId, categoryDto));
    }
}
