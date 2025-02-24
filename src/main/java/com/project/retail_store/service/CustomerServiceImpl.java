package com.project.retail_store.service;

import com.project.retail_store.converter.CustomerMapper;
import com.project.retail_store.dtos.CustomerDto;
import com.project.retail_store.entity.Customer;
import com.project.retail_store.repository.CustomerRepository;
import com.project.retail_store.service.interfaces.CustomerService;
import com.project.retail_store.util.CommonUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService, UserDetailsService {

    private final CustomerRepository customerRepository;

    @Override
    public CustomerDto getCustomerProfile(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> CommonUtils.logAndGetException("Customer not found with email: " + id));
        return CustomerMapper.INSTANCE.toDto(customer);
    }

    @Override
    @Transactional
    public CustomerDto updateCustomerProfile(Long id, CustomerDto customerDto) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> CommonUtils.logAndGetException("Customer not found with email: " + id));

        customer.setName(Objects.nonNull(customerDto.getName()) ? customerDto.getName() :customer.getName());
        customer.setPhone(Objects.nonNull(customerDto.getPhone()) ? customerDto.getPhone() :customer.getPhone());
        customer.setEmail(Objects.nonNull(customerDto.getEmail()) ? customerDto.getEmail() :customer.getEmail());
        customer.setAddress(Objects.nonNull(customerDto.getAddress()) ? customerDto.getAddress() :customer.getAddress());

        customer.setDateModified(LocalDateTime.now());
        customer.setUserModified(customerDto.getId());

        Customer updatedCustomer = customerRepository.save(customer);
        return CustomerMapper.INSTANCE.toDto(updatedCustomer);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(() -> CommonUtils.logAndGetException("User not found with email: " + email));

        return new User(
                customer.getEmail(),
                customer.getPassword(),
                customer.isActive(),  // Account is enabled if active
                true,  // Account is not expired
                true,  // Credentials are not expired
                true,  // Account is not locked
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_CUSTOMER"))
        );
    }

    @Override
    public List<CustomerDto> getAllCustomers() {
        return customerRepository.findAll().stream()
                .map(CustomerMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void disableCustomer(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> CommonUtils.logAndGetException("Customer not found with ID: " + customerId));

        customer.setActive(false);
        customer.setDateModified(LocalDateTime.now());
        customer.setUserModified(customerId);

        customerRepository.save(customer);
    }

}