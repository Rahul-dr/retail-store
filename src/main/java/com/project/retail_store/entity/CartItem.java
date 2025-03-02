package com.project.retail_store.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cart_items")
public class CartItem {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "customer_id")
  @JsonIgnore
  private Customer customer;

  @ManyToOne
  @JoinColumn(name = "product_id")
  @JsonIgnore
  private Product product;

  private Integer quantity;

  private LocalDateTime dateCreated;
  private String userCreated;
  private LocalDateTime dateModified;
  private String userModified;
  private boolean active = true;
}
