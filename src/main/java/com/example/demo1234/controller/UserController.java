package com.example.demo1234.controller;

import com.example.demo1234.dto.UpdateProfileRequest;
import com.example.demo1234.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PutMapping("/update-profile")
    @PreAuthorize("hasRole('CUSTOMER')")
    public String updateProfile(@RequestBody UpdateProfileRequest request) {
        userService.updateProfile(request);
        return "Profile updated successfully!";
    }
}
