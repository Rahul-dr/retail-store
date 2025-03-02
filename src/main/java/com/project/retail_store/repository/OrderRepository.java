package com.project.retail_store.repository;

import com.project.retail_store.entity.Order;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
  List<Order> findByCustomerId(Long customerId);

  Order findByOrderNumber(String orderNumber);
}
