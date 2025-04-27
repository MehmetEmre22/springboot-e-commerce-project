package com.example.demo1234.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DtoBookIU {
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
