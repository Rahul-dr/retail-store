package com.project.retail_store.service.interfaces;

import com.project.retail_store.dtos.OrderDTO;
import com.project.retail_store.dtos.PlaceOrderRequest;

import java.util.List;

public interface OrderService {
    OrderDTO placeOrder(PlaceOrderRequest request);
    List<OrderDTO> getCustomerOrders(Long customerId);
    OrderDTO getOrderDetails(Long customerId, Long orderId);
}