package com.project.retail_store.controller;

import com.project.retail_store.dtos.CustomerDto;
import com.project.retail_store.dtos.ApiResponse;
import com.project.retail_store.service.interfaces.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @GetMapping("/{customerId}")
    public ApiResponse<CustomerDto> getProfile(@PathVariable Long customerId) {
        return new ApiResponse<>("Profile retrieved successfully", customerService.getCustomerProfile(customerId));
    }

    @PutMapping("/{customerId}")
    public ApiResponse<CustomerDto> updateProfile(
            @PathVariable Long customerId,
            @RequestBody CustomerDto customerDto) {
        return new ApiResponse<>("Profile updated successfully", customerService.updateCustomerProfile(customerId,customerDto));
    }

    @GetMapping("/admin")
    public ApiResponse<List<CustomerDto>> getAllCustomers() {
        return new ApiResponse<>("Customers retrieved successfully", customerService.getAllCustomers());
    }

    @DeleteMapping("/admin/disable/{customerId}")
    public ApiResponse<String> disableCustomer(@PathVariable Long customerId) {
        customerService.disableCustomer(customerId);
        return new ApiResponse<>("Customer disabled successfully", "Customer with ID " + customerId + " has been disabled.");
    }
}