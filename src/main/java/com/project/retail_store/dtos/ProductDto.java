package com.project.retail_store.dtos;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class ProductDto {
  private Long id;
  private String name;
  private String description;
  private BigDecimal price;
  private String imageUrl;
  private Integer stockQuantity;
  private Long categoryId;
  private Long sellerId;
  private boolean active;
}
