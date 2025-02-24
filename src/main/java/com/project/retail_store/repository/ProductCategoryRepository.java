package com.project.retail_store.repository;

import com.project.retail_store.entity.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {
    List<ProductCategory> findByActiveTrue();
    Optional<ProductCategory> findById(Long id);
}