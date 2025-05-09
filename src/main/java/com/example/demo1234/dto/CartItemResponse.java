package com.example.demo1234.dto;

import com.example.demo1234.model.Book;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CartItemResponse {
    private Long cartItemId;
    private Book book;
}
