package com.project.retail_store.repository;

import com.project.retail_store.entity.Product;
import com.project.retail_store.entity.ProductCategory;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
  List<Product> findByCategory(ProductCategory category);

  List<Product> findByActiveTrue();
}
