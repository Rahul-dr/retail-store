package com.project.retail_store.service.interfaces;

import com.project.retail_store.dtos.CustomerDto;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface CustomerService {

    CustomerDto getCustomerProfile(Long id);

    CustomerDto updateCustomerProfile(Long id, CustomerDto customerDto);

    UserDetails loadUserByUsername(String email);

    List<CustomerDto> getAllCustomers();

    void disableCustomer(Long customerId);
}
