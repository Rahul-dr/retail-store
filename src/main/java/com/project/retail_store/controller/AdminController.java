package com.project.retail_store.controller;

import com.project.retail_store.dtos.ApiResponse;
import com.project.retail_store.dtos.CustomerDto;
import com.project.retail_store.dtos.ProductCategoryDto;
import com.project.retail_store.dtos.ProductDto;
import com.project.retail_store.dtos.SellerDto;
import com.project.retail_store.service.interfaces.*;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {
  private final SellerService sellerService;
  private final ProductCategoryService categoryService;
  private final ProductService productService;
  private final CustomerService customerService;

  // 🔹 Seller Management
  @PostMapping("/sellers/add")
  public ApiResponse<SellerDto> addSeller(@RequestBody SellerDto sellerDto) {
    return new ApiResponse<>("Seller added", sellerService.addSeller(sellerDto, "Admin"));
  }

  @GetMapping("/sellers")
  public ApiResponse<List<SellerDto>> getAllSellers() {
    return new ApiResponse<>("Sellers retrieved", sellerService.getAllSellers());
  }

  @PutMapping("/sellers/update/{sellerId}")
  public ApiResponse<SellerDto> updateSeller(
      @PathVariable Long sellerId, @RequestBody SellerDto sellerDto) {
    return new ApiResponse<>(
        "Seller updated", sellerService.updateSeller(sellerId, sellerDto, "Admin"));
  }

  @DeleteMapping("/sellers/disable/{sellerId}")
  public ApiResponse<String> disableSeller(@PathVariable Long sellerId) {
    sellerService.disableSeller(sellerId, "Admin");
    return new ApiResponse<>("Seller disabled", "ID: " + sellerId);
  }

  // 🔹 Product Category Management
  @PostMapping("/categories/add")
  public ApiResponse<ProductCategoryDto> addCategory(@RequestBody ProductCategoryDto categoryDto) {
    return new ApiResponse<>("Category added", categoryService.addCategory(categoryDto));
  }

  @GetMapping("/categories")
  public ApiResponse<List<ProductCategoryDto>> getAllCategories() {
    return new ApiResponse<>("Categories retrieved", categoryService.getAllCategories());
  }

  @PutMapping("/categories/update/{categoryId}")
  public ApiResponse<ProductCategoryDto> updateCategory(
      @PathVariable Long categoryId, @RequestBody ProductCategoryDto categoryDto) {
    return new ApiResponse<>(
        "Category updated", categoryService.updateCategory(categoryId, categoryDto));
  }

  // 🔹 Product Management
  @PostMapping("/products/add")
  public ApiResponse<ProductDto> addProduct(@RequestBody ProductDto productDto) {
    return new ApiResponse<>("Product added", productService.addProduct(productDto));
  }

  @GetMapping("/products")
  public ApiResponse<List<ProductDto>> getAllProducts() {
    return new ApiResponse<>("Products retrieved", productService.getAllProducts());
  }

  @PutMapping("/products/update/{productId}")
  public ApiResponse<ProductDto> updateProduct(
      @PathVariable Long productId, @RequestBody ProductDto productDto) {
    return new ApiResponse<>(
        "Product updated", productService.updateProduct(productId, productDto));
  }

  @DeleteMapping("/products/disable/{productId}")
  public ApiResponse<String> disableProduct(@PathVariable Long productId) {
    productService.disableProduct(productId);
    return new ApiResponse<>("Product disabled", "ID: " + productId);
  }

  // 🔹 Customer Management
  @GetMapping("/customers")
  public ApiResponse<List<CustomerDto>> getAllCustomers() {
    return new ApiResponse<>("Customers retrieved", customerService.getAllCustomers());
  }

  @DeleteMapping("/customers/disable/{customerId}")
  public ApiResponse<String> disableCustomer(@PathVariable Long customerId) {
    customerService.disableCustomer(customerId);
    return new ApiResponse<>("Customer disabled", "ID: " + customerId);
  }
}
