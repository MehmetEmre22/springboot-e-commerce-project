package com.example.demo1234.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cart_item")
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user; // Hangi kullanıcıya ait

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book; // Hangi ürüne ait

    private Integer quantity;
}
