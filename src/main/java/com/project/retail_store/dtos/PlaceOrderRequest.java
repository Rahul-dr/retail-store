package com.project.retail_store.dtos;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlaceOrderRequest {
  private Long customerId;
  private List<OrderItemDTO> items;
}
