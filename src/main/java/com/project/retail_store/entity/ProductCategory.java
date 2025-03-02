package com.project.retail_store.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;

@Entity
@Data
@Table(name = "product_categories")
public class ProductCategory {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;
  private String description;

  @OneToMany(mappedBy = "category")
  @JsonIgnore
  private List<Product> products;

  private LocalDateTime dateCreated;
  private String userCreated;
  private LocalDateTime dateModified;
  private String userModified;
  private boolean active = true;
}
