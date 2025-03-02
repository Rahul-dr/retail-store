package com.project.retail_store.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;

@Entity
@Data
@Table(name = "sellers")
public class Seller {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;
  private String email;
  private String phone;
  private String address;
  private String description;

  @OneToMany(mappedBy = "seller")
  private List<Product> products;

  private LocalDateTime dateCreated;
  private String userCreated;
  private LocalDateTime dateModified;
  private String userModified;
  private boolean active = true;
}
