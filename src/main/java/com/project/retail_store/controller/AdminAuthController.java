package com.project.retail_store.controller;

import com.project.retail_store.dtos.ApiResponse;
import com.project.retail_store.dtos.AuthRequest;
import com.project.retail_store.dtos.AuthResponse;
import com.project.retail_store.entity.User;
import com.project.retail_store.Enum.ERole;
import com.project.retail_store.repository.UserRepository;
import com.project.retail_store.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminAuthController {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ApiResponse<AuthResponse> adminLogin(@RequestBody AuthRequest request) {
        User admin = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Admin not found"));

        // ✅ Fix: Compare role directly
        if (admin.getRole() != ERole.ROLE_ADMIN) {
            throw new RuntimeException("Unauthorized access");
        }

        // ✅ Fix: Password verification
        if (!passwordEncoder.matches(request.getPassword(), admin.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        // ✅ Fix: Ensure JWT Util supports role-based token creation
        String token = jwtUtil.generateToken(admin.getEmail(), admin.getRole().name());

        return new ApiResponse<>("Admin login successful", new AuthResponse(token, admin.getEmail(), admin.getRole().name()));
    }
}
