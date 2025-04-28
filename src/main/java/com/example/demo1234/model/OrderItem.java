package com.example.demo1234.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "order_item")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order; // Hangi siparişe bağlı

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book; // Hangi ürün

    private Integer quantity;

    private Double price; // O anki ürün fiyatı
}
