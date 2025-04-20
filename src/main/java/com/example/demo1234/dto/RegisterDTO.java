package com.example.demo1234.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegisterDTO {

    @NotBlank(message="Kullanıcı adı boş bırakılamaz")
    private String username;
    @NotBlank(message = "email boş bıraklılamaz")
    private String email;
    @NotBlank(message="şifre boş bırakılamaz")
    private String password;
}
