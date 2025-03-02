package com.project.retail_store.service.interfaces;

import com.project.retail_store.dtos.CustomerDto;
import java.util.List;
import org.springframework.security.core.userdetails.UserDetails;

public interface CustomerService {

  CustomerDto getCustomerProfile(Long id);

  CustomerDto updateCustomerProfile(Long id, CustomerDto customerDto);

  UserDetails loadUserByUsername(String email);

  List<CustomerDto> getAllCustomers();

  void disableCustomer(Long customerId);
}
