package com.example.demo1234.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "app_user")  // Changed from 'user' to 'app_user'
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="user_name",nullable = false,unique = false)
    private String username;

    @Column(name="email",nullable = false,unique = true)
    private String email;


}
