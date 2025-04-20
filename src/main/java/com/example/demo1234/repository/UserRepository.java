package com.example.demo1234.repository;

import com.example.demo1234.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
