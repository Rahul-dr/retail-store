package com.project.retail_store.service;

import com.project.retail_store.dtos.AuthRequest;
import com.project.retail_store.dtos.AuthResponse;
import com.project.retail_store.dtos.RegisterRequest;
import com.project.retail_store.entity.Customer;
import com.project.retail_store.repository.CustomerRepository;
import com.project.retail_store.service.interfaces.AuthService;
import com.project.retail_store.service.interfaces.CustomerService;
import com.project.retail_store.util.CommonUtils;
import com.project.retail_store.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final CustomerService customerService;

    @Override
    @Transactional
    public AuthResponse register(RegisterRequest request) {
        // Check if email already exists
        if (customerRepository.existsByEmail(request.getEmail())) {
            throw CommonUtils.logAndGetException("Email already registered");
        }

        // Create new customer
        Customer customer = new Customer();
        customer.setEmail(request.getEmail());
        customer.setPassword(passwordEncoder.encode(request.getPassword()));
        customer.setName(request.getName());
        customer.setPhone(request.getPhone());
        customer.setDateCreated(LocalDateTime.now());
        customer.setUserCreated(request.getEmail());
        customer.setActive(true);

        // Save customer
        customerRepository.save(customer);

        // Generate JWT token
        UserDetails userDetails = customerService.loadUserByUsername(request.getEmail());
        String token = jwtUtil.generateToken(userDetails);

        // Create response
        AuthResponse response = new AuthResponse();
        response.setToken(token);
        response.setEmail(customer.getEmail());
        response.setRole("ROLE_CUSTOMER");

        return response;
    }

    @Override
    public AuthResponse login(AuthRequest request) {
        // Find customer by email
        Customer customer = customerRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> CommonUtils.logAndGetException("Invalid email or password"));

        // Check if customer is active
        if (!customer.isActive()) {
            throw CommonUtils.logAndGetException("Account is disabled");
        }

        // Verify password
        if (!passwordEncoder.matches(request.getPassword(), customer.getPassword())) {
            throw CommonUtils.logAndGetException("Invalid email or password");
        }

        // Generate JWT token
        UserDetails userDetails = customerService.loadUserByUsername(request.getEmail());
        String token = jwtUtil.generateToken(userDetails);

        // Create response
        AuthResponse response = new AuthResponse();
        response.setToken(token);
        response.setEmail(customer.getEmail());
        response.setRole("ROLE_CUSTOMER");

        return response;
    }
}