package com.example.demo1234.repository;

import com.example.demo1234.model.Book;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository

public interface BookRepository extends JpaRepository<Book,Long> {
    Optional<Book> findByIsbn(Long isbn);
    void deleteByIsbn(Long isbn);
}
