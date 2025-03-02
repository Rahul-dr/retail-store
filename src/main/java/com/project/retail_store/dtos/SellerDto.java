package com.project.retail_store.dtos;

import lombok.Data;

@Data
public class SellerDto {
  private Long id;
  private String name;
  private String email;
  private String phone;
  private String address;
  private String description;
  private boolean active;
}
