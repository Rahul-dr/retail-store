package com.project.retail_store.service;

import com.project.retail_store.Enum.ERole;
import com.project.retail_store.dtos.AuthRequest;
import com.project.retail_store.dtos.AuthResponse;
import com.project.retail_store.dtos.RegisterRequest;
import com.project.retail_store.entity.Customer;
import com.project.retail_store.entity.User;
import com.project.retail_store.repository.CustomerRepository;
import com.project.retail_store.repository.UserRepository;
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
//
//@Service
//@RequiredArgsConstructor
//public class AuthServiceImpl implements AuthService {
//
//    private final CustomerRepository customerRepository;
//    private final PasswordEncoder passwordEncoder;
//    private final JwtUtil jwtUtil;
//    private final CustomerService customerService;
//
//    @Override
//    @Transactional
//    public AuthResponse register(RegisterRequest request) {
//
//        if (customerRepository.existsByEmail(request.getEmail())) {
//            throw CommonUtils.logAndGetException("Email already registered");
//        }
//
//        // Create new customer
//        Customer customer = new Customer();
//        customer.setEmail(request.getEmail());
//        customer.setPassword(passwordEncoder.encode(request.getPassword()));
//        customer.setName(request.getName());
//        customer.setPhone(request.getPhone());
//        customer.setDateCreated(LocalDateTime.now());
//        customer.setUserCreated(request.getEmail());
//        customer.setActive(true);
//
//        // Save customer
//        customerRepository.save(customer);
//
//        // Generate JWT token
//        UserDetails userDetails = customerService.loadUserByUsername(request.getEmail());
//        String token = jwtUtil.generateToken(userDetails);
//
//        // Create response
//        AuthResponse response = new AuthResponse();
//        response.setToken(token);
//        response.setEmail(customer.getEmail());
//        response.setRole("ROLE_CUSTOMER");
//
//        return response;
//    }
//
//    @Override
//    public AuthResponse login(AuthRequest request) {
//        // Find customer by email
//        Customer customer = customerRepository.findByEmail(request.getEmail())
//                .orElseThrow(() -> CommonUtils.logAndGetException("Invalid email or password"));
//
//        // Check if customer is active
//        if (!customer.isActive()) {
//            throw CommonUtils.logAndGetException("Account is disabled");
//        }
//
//        // Verify password
//        if (!passwordEncoder.matches(request.getPassword(), customer.getPassword())) {
//            throw CommonUtils.logAndGetException("Invalid email or password");
//        }
//
//        // Generate JWT token
//        UserDetails userDetails = customerService.loadUserByUsername(request.getEmail());
//        String token = jwtUtil.generateToken(userDetails);
//
//        // Create response
//        AuthResponse response = new AuthResponse();
//        response.setToken(token);
//        response.setEmail(customer.getEmail());
//        response.setRole("ROLE_CUSTOMER");
//
//        return response;
//    }
//}

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;  // ✅ Fix: Use UserRepository
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Override
    @Transactional
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already registered");
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setName(request.getName());
        user.setRole(ERole.ROLE_CUSTOMER);
        user.setDateCreated(LocalDateTime.now());

        userRepository.save(user);

        String token = jwtUtil.generateToken(user.getEmail(), user.getRole().name());

        return new AuthResponse(token, user.getEmail(), user.getRole().name());
    }

    @Override
    public AuthResponse login(AuthRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        String token = jwtUtil.generateToken(user.getEmail(), user.getRole().name());

        return new AuthResponse(token, user.getEmail(), user.getRole().name());
    }
}
