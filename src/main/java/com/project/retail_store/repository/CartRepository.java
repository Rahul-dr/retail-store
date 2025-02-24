package com.project.retail_store.repository;

import com.project.retail_store.entity.CartItem;
import com.project.retail_store.entity.Customer;
import com.project.retail_store.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findByCustomerAndActiveTrue(Customer customer);
    Optional<CartItem> findByCustomerAndProductAndActiveTrue(Customer customer, Product product);
}