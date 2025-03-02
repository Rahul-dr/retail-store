package com.project.retail_store.repository;

import com.project.retail_store.entity.Customer;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
  Optional<Customer> findByEmail(String email);

  Optional<Customer> findById(Long id);

  boolean existsByEmail(String email);
}
