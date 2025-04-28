package com.example.demo1234.controller;

import com.example.demo1234.model.CartItem;
import com.example.demo1234.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RequiredArgsConstructor
@RestController
public class CartController {
    private final CartService cartService;
    @GetMapping("/get")
    public List<CartItem> getCartItems() {
        return cartService.getCartItemsForCurrentUser();
    }

}
