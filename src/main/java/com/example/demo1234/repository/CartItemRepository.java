package com.example.demo1234.repository;
import com.example.demo1234.model.Book;
import com.example.demo1234.model.CartItem;
import com.example.demo1234.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findByUser(User user);
    List<CartItem> findByBook(Book book);

    Optional<CartItem> findByUserAndBook(User user, Book book);
}
