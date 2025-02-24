package com.project.retail_store.repository;

import com.project.retail_store.entity.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface SellerRepository extends JpaRepository<Seller, Long> {

    List<Seller> findAll();

    Optional<Seller> findById(Long id);

    Optional<Seller> findByEmail(String email);
}
