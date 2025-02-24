package com.project.retail_store.service.interfaces;

import com.project.retail_store.dtos.AuthRequest;
import com.project.retail_store.dtos.AuthResponse;
import com.project.retail_store.dtos.RegisterRequest;

public interface AuthService {

    AuthResponse register(RegisterRequest request);

    AuthResponse login(AuthRequest request);
}