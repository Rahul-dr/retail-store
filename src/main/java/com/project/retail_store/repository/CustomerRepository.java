package com.project.retail_store.repository;

import com.project.retail_store.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByEmail(String email);

    Optional<Customer> findById(Long id);

    boolean existsByEmail(String email);
}