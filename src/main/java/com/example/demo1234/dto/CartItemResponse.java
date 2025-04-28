package com.example.demo1234.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CartItemResponse {
    private Long cartItemId;
    private String bookTitle;
    private Double bookPrice;
    private Integer quantity;
}
