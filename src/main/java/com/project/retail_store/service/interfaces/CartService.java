package com.project.retail_store.service.interfaces;

import com.project.retail_store.entity.CartItem;

import java.util.List;

public interface CartService {
    CartItem addToCart(Long customerId, Long productId, Integer quantity);
    List<CartItem> getCustomerCart(Long customerId);
    CartItem updateQuantity(Long customerId, Long productId, Integer newQuantity);
    void removeFromCart(Long customerId, Long productId);
}
