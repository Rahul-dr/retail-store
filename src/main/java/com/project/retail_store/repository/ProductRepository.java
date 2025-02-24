package com.project.retail_store.repository;

import com.project.retail_store.entity.Product;
import com.project.retail_store.entity.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategory(ProductCategory category);
    List<Product> findByActiveTrue();
}
