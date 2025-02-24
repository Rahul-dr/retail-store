// CartController.java
package com.project.retail_store.controller;

import com.project.retail_store.dtos.ApiResponse;
import com.project.retail_store.dtos.CartRequest;
import com.project.retail_store.entity.CartItem;
import com.project.retail_store.service.interfaces.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @PostMapping("/add")
    public ApiResponse<CartItem> addToCart(@RequestBody CartRequest request) {
        return new ApiResponse<>(
                "Product added to cart successfully",
                cartService.addToCart(request.getCustomerId(), request.getProductId(), request.getQuantity())
        );
    }

    @GetMapping("/{customerId}")
    public ApiResponse<List<CartItem>> viewCart(@PathVariable Long customerId) {
        return new ApiResponse<>(
                "Cart retrieved successfully",
                cartService.getCustomerCart(customerId)
        );
    }

    @PutMapping("/update")
    public ApiResponse<CartItem> updateCartItem(@RequestBody CartRequest request) {
        return new ApiResponse<>(
                "Cart item updated successfully",
                cartService.updateQuantity(request.getCustomerId(), request.getProductId(), request.getQuantity())
        );
    }

    @DeleteMapping("/remove/{productId}")
    public ApiResponse<Void> removeFromCart(@PathVariable Long productId, @RequestParam Long customerId) {
        cartService.removeFromCart(customerId, productId);
        return new ApiResponse<>("Product removed from cart successfully", null);
    }
}