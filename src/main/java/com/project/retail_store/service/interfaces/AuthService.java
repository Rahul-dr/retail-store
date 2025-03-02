package com.project.retail_store.service.interfaces;

import com.project.retail_store.dtos.AuthRequest;
import com.project.retail_store.dtos.AuthResponse;
import com.project.retail_store.dtos.RegisterRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface AuthService {

    UserDetails loadUserByUsername(String email) throws UsernameNotFoundException;

    AuthResponse register(RegisterRequest request);

    AuthResponse login(AuthRequest request);
}