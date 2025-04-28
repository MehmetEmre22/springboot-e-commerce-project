package com.example.demo1234.controller;

import com.example.demo1234.dto.OrderResponse;
import com.example.demo1234.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/place")
    public String placeOrder() {
        orderService.placeOrder();
        return "Order placed successfully!";
    }

    @GetMapping("/my-orders")
    public List<OrderResponse> getUserOrders() {
        return orderService.getUserOrders();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all-orders")
    public List<OrderResponse> getAllOrders() {
        return orderService.getAllOrders();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/admin/approve/{orderId}")
    public String approveOrder(@PathVariable Long orderId) {
        orderService.approveOrder(orderId);
        return "Order approved successfully!";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/admin/reject/{orderId}")
    public String rejectOrder(@PathVariable Long orderId) {
        orderService.rejectOrder(orderId);
        return "Order rejected successfully!";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/approved-orders")
    public List<OrderResponse> getApprovedOrders() {
        return orderService.getApprovedOrders();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/rejected-orders")
    public List<OrderResponse> getRejectedOrders() {
        return orderService.getRejectedOrders();
    }
}
