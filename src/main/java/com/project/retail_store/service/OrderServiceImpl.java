package com.project.retail_store.service;

import com.project.retail_store.Enum.OrderStatus;
import com.project.retail_store.converter.OrderMapper;
import com.project.retail_store.dtos.OrderDTO;
import com.project.retail_store.dtos.OrderItemDTO;
import com.project.retail_store.dtos.PlaceOrderRequest;
import com.project.retail_store.entity.Customer;
import com.project.retail_store.entity.Order;
import com.project.retail_store.entity.OrderItem;
import com.project.retail_store.entity.Product;
import com.project.retail_store.repository.CustomerRepository;
import com.project.retail_store.repository.OrderRepository;
import com.project.retail_store.repository.ProductRepository;
import com.project.retail_store.service.interfaces.OrderService;
import com.project.retail_store.util.CommonUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final OrderMapper orderMapper;

    @Override
    @Transactional
    public OrderDTO placeOrder(PlaceOrderRequest request) {
        if (request.getItems() == null || request.getItems().isEmpty()) {
            throw CommonUtils.logAndGetException("Order must contain at least one item");
        }

        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> CommonUtils.logAndGetException("Customer not found with id: " + request.getCustomerId()));

        Order order = new Order();
        order.setCustomer(customer);
        order.setOrderNumber(generateOrderNumber());
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(OrderStatus.PENDING.name());
        order.setDateCreated(LocalDateTime.now());
        order.setUserCreated(customer.getEmail());

        List<OrderItem> orderItems = request.getItems().stream()
                .map(item -> createOrderItem(item, order))
                .collect(Collectors.toList());

        order.setOrderItems(orderItems);
        order.setTotalAmount(calculateTotalAmount(orderItems));

        Order savedOrder = orderRepository.save(order);
        return orderMapper.toDto(savedOrder);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderDTO> getCustomerOrders(Long customerId) {
        return orderRepository.findByCustomerId(customerId)
                .stream().map(orderMapper::toDto).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public OrderDTO getOrderDetails(Long customerId, Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> CommonUtils.logAndGetException("Order not found with id: " + orderId));

        if (!order.getCustomer().getId().equals(customerId)) {
            throw CommonUtils.logAndGetException("Order does not belong to customer with id: " + customerId);
        }

        return orderMapper.toDto(order);
    }

    private OrderItem createOrderItem(OrderItemDTO item, Order order) {
        Product product = productRepository.findById(item.getProductId())
                .orElseThrow(() -> CommonUtils.logAndGetException("Product not found with id: " + item.getProductId()));

        OrderItem orderItem = new OrderItem();
        orderItem.setOrder(order);
        orderItem.setProduct(product);
        orderItem.setQuantity(item.getQuantity());
        orderItem.setPrice(product.getPrice());
        orderItem.setSubtotal(product.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));

        return orderItem;
    }

    private String generateOrderNumber() {
        return UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    private BigDecimal calculateTotalAmount(List<OrderItem> items) {
        return items.stream()
                .map(OrderItem::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
