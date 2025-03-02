package com.project.retail_store.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;

@Entity
@Data
@Table(name = "products")
public class Product {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;
  private String description;
  private BigDecimal price;
  private String imageUrl;
  private Integer stockQuantity;

  @ManyToOne
  @JoinColumn(name = "category_id")
  @JsonIgnore
  private ProductCategory category;

  @ManyToOne
  @JoinColumn(name = "seller_id")
  private Seller seller;

  private LocalDateTime dateCreated;
  private String userCreated;
  private LocalDateTime dateModified;
  private String userModified;
  private boolean active = true;
}
