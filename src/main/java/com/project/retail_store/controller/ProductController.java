package com.project.retail_store.controller;

import com.project.retail_store.dtos.ApiResponse;
import com.project.retail_store.dtos.ProductDto;
import com.project.retail_store.service.interfaces.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    // Add Product
    @PostMapping("/add")
    public ApiResponse<ProductDto> addProduct(@RequestBody ProductDto productDto) {
        return new ApiResponse<>("Product added successfully", productService.addProduct(productDto));
    }

    // View All Products
    @GetMapping
    public ApiResponse<List<ProductDto>> getAllProducts() {
        return new ApiResponse<>("Products retrieved successfully", productService.getAllProducts());
    }

    // Update Product
    @PutMapping("/update/{productId}")
    public ApiResponse<ProductDto> updateProduct(@PathVariable Long productId, @RequestBody ProductDto productDto) {
        return new ApiResponse<>("Product updated successfully", productService.updateProduct(productId, productDto));
    }

    // Disable Product
    @DeleteMapping("/disable/{productId}")
    public ApiResponse<String> disableProduct(@PathVariable Long productId) {
        productService.disableProduct(productId);
        return new ApiResponse<>("Product disabled successfully", "Product with ID " + productId + " has been disabled.");
    }
}
