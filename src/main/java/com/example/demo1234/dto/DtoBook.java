package com.example.demo1234.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DtoBook {
    private Long isbn;
    private String title;
    private String author;
    private String category;
    private Integer quantity;
    private String description;
    private String publisher;
    private Double price;
    private String image;
}
