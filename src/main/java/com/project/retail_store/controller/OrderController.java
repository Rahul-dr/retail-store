package com.project.retail_store.controller;

import com.project.retail_store.dtos.ApiResponse;
import com.project.retail_store.dtos.OrderDTO;
import com.project.retail_store.dtos.PlaceOrderRequest;
import com.project.retail_store.service.interfaces.OrderService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
  private final OrderService orderService;

  @PostMapping("/place")
  public ApiResponse<OrderDTO> placeOrder(@RequestBody PlaceOrderRequest request) {
    return new ApiResponse<>("Order placed successfully", orderService.placeOrder(request));
  }

  @GetMapping("/customer/{customerId}")
  public ApiResponse<List<OrderDTO>> getCustomerOrders(@PathVariable Long customerId) {
    return new ApiResponse<>(
        "Orders retrieved successfully", orderService.getCustomerOrders(customerId));
  }

  @GetMapping("/{customerId}/{orderId}")
  public ApiResponse<OrderDTO> getOrderDetails(
      @PathVariable Long customerId, @PathVariable Long orderId) {
    return new ApiResponse<>(
        "Order details retrieved successfully", orderService.getOrderDetails(customerId, orderId));
  }
}
