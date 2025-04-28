package com.example.demo1234.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderItemResponse {
    private String bookTitle;
    private Integer quantity;
    private Double pricePerUnit;
}
