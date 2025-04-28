package com.example.demo1234.service;

import com.example.demo1234.dto.RegisterRequest;
import com.example.demo1234.enums.Role;
import com.example.demo1234.model.User;
import com.example.demo1234.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void register(RegisterRequest request) {
        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword())) // şifre encode edilir
                .role(Role.ROLE_CUSTOMER)
                .build();
        userRepository.save(user);
    }

}
