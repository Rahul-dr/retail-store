package com.project.retail_store.controller;

import com.project.retail_store.dtos.AuthRequest;
import com.project.retail_store.dtos.AuthResponse;
import com.project.retail_store.dtos.RegisterRequest;
import com.project.retail_store.dtos.ApiResponse;
import com.project.retail_store.service.interfaces.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ApiResponse<AuthResponse> register(@RequestBody RegisterRequest request) {
        AuthResponse response = authService.register(request);
        return new ApiResponse<>("Registration successful", response);
    }

    @PostMapping("/login")
    public ApiResponse<AuthResponse> login(@RequestBody AuthRequest request) {
        String hashedPassword = new BCryptPasswordEncoder().encode("1100");
        System.out.println(hashedPassword);
        AuthResponse response = authService.login(request);
        return new ApiResponse<>("Login successful", response);
    }
}