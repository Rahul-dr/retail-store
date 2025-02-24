package com.project.retail_store.service;

import com.project.retail_store.entity.CartItem;
import com.project.retail_store.entity.Customer;
import com.project.retail_store.entity.Product;
import com.project.retail_store.repository.CartRepository;
import com.project.retail_store.repository.CustomerRepository;
import com.project.retail_store.repository.ProductRepository;
import com.project.retail_store.service.interfaces.CartService;
import com.project.retail_store.util.CommonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;

    @Transactional
    @Override
    public CartItem addToCart(Long customerId, Long productId, Integer quantity) {
        Customer customer = customerRepository.findById(customerId)
            .orElseThrow(() -> CommonUtils.logAndGetException("Customer not found with ID: " + customerId));
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> CommonUtils.logAndGetException("Product not found with ID: " + productId));

        CartItem cartItem = cartRepository
            .findByCustomerAndProductAndActiveTrue(customer, product)
            .orElse(new CartItem());

        if (cartItem.getId() == null) {
            cartItem.setCustomer(customer);
            cartItem.setProduct(product);
            cartItem.setDateCreated(LocalDateTime.now());
            cartItem.setUserCreated("SYSTEM");
        }

        cartItem.setQuantity(quantity);
        cartItem.setDateModified(LocalDateTime.now());
        cartItem.setUserModified("SYSTEM");

        return cartRepository.save(cartItem);
    }

    @Override
    public List<CartItem> getCustomerCart(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
            .orElseThrow(() -> CommonUtils.logAndGetException("Customer not found with ID: " + customerId));
        return cartRepository.findByCustomerAndActiveTrue(customer);
    }

    @Transactional
    @Override
    public CartItem updateQuantity(Long customerId, Long productId, Integer newQuantity) {
        Customer customer = customerRepository.findById(customerId)
            .orElseThrow(() -> CommonUtils.logAndGetException("Customer not found with ID: " + customerId));
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> CommonUtils.logAndGetException("Product not found with ID: " + productId));

        CartItem cartItem = cartRepository
            .findByCustomerAndProductAndActiveTrue(customer, product)
            .orElseThrow(() -> CommonUtils.logAndGetException("Cart item not found for customer ID: " +
                 customerId + " and product ID: " + productId));

        cartItem.setQuantity(newQuantity);
        cartItem.setDateModified(LocalDateTime.now());
        cartItem.setUserModified("SYSTEM");

        return cartRepository.save(cartItem);
    }

    @Transactional
    @Override
    public void removeFromCart(Long customerId, Long productId) {
        Customer customer = customerRepository.findById(customerId)
            .orElseThrow(() -> CommonUtils.logAndGetException("Customer not found with ID: " + customerId));
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> CommonUtils.logAndGetException("Product not found with ID: " + productId));

        CartItem cartItem = cartRepository
            .findByCustomerAndProductAndActiveTrue(customer, product)
            .orElseThrow(() -> CommonUtils.logAndGetException("Cart item not found for customer ID: " +
                 customerId + " and product ID: " + productId));

        cartItem.setActive(false);
        cartItem.setDateModified(LocalDateTime.now());
        cartItem.setUserModified("SYSTEM");

        cartRepository.save(cartItem);
    }
}
