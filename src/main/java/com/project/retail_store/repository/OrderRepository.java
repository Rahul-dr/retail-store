package com.project.retail_store.repository;

import com.project.retail_store.entity.Order;
import com.project.retail_store.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByCustomerId(Long customerId);
    Order findByOrderNumber(String orderNumber);
}