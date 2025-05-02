package com.example.demo1234.model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "books")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "isbn", unique = true)
    private Long isbn;

    @Column(name = "title")
    private String title;

    @Column(name = "author")
    private String author;

    @Column(name = "category")
    private String category;

    @Column(name = "quantity" )
    private Integer quantity;

    @Column(name = "description")
    private String description;

    @Column(name = "image")
    private String image;

    @Column(name = "publisher")
    private String publisher;

    @Column(name = "price")
    private Double price;
}
