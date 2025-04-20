package com.example.demo1234.service;

import com.example.demo1234.dto.RegisterDTO;
import com.example.demo1234.model.User;
import com.example.demo1234.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void register(RegisterDTO dto){
        if(userRepository.findByUsername(dto.getUsername()).isPresent()){
            throw new RuntimeException("kullanıcı zaten mevcut");
        }
        User user= User.builder().username(dto.getUsername()).password(dto.getPassword()).email(dto.getEmail()).build();
        userRepository.save(user);
    }
}
