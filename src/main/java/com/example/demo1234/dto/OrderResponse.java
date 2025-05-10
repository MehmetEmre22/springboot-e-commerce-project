package com.example.demo1234.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class OrderResponse {
    private Long orderId;
    private Double totalPrice;
    private LocalDateTime createdAt;
    private String status;
    private String username;  //  Siparişi veren kullanıcının adı.
    private String email;     //  Siparişi veren kullanıcının emaili.
    private List<OrderItemResponse> items;
}
